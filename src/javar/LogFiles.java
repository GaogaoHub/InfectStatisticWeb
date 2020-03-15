package javar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.TreeSet;

public class LogFiles {
    String lastDate;
    TreeSet<File> files;

    public LogFiles(String path) {
        File logFile = new File(path);
        File[] temp = logFile.listFiles();
        files = new TreeSet<File>(new Comparator<File>() {
            @Override
            // 文件按日期排序 重写匿名内部类Comparator的compare()
            public int compare(File f0, File f1) {
                String name0 = f0.getName();
                String name1 = f1.getName();
                // 按年份月份日期依次比较时间前后
                if (Integer.parseInt(name0.substring(0, 4)) < Integer.parseInt(name1.substring(0, 4))) {
                    return -1;
                }
                if (Integer.parseInt(name0.substring(0, 4)) > Integer.parseInt(name1.substring(0, 4))) {
                    return 1;
                }
                if (Integer.parseInt(name0.substring(5, 7)) < Integer.parseInt(name1.substring(5, 7))) {
                    return -1;
                }
                if (Integer.parseInt(name0.substring(5, 7)) > Integer.parseInt(name1.substring(5, 7))) {
                    return 1;
                }
                if (Integer.parseInt(name0.substring(8, 10)) < Integer.parseInt(name1.substring(8, 10))) {
                    return -1;
                }
                if (Integer.parseInt(name0.substring(8, 10)) > Integer.parseInt(name1.substring(8, 10))) {
                    return 1;
                }
                return 0;
            }

        });
        for (int i = 0; i < temp.length; ++i) {
            files.add(temp[i]);
        }

        lastDate = files.last().getName().substring(0, 10);
    }

    public void readFiles(String date, Statistic sta) {
        // 默认最新日期
        if (date.equals("lastdate")) {
            for (File f : files) {
                LogFiles.statisFile(f, sta);
            }
            
            // 各省数据统计到全国
            int[] all = { 0, 0, 0, 0 };
            for (String keytemp : sta.data.keySet()) {
                int[] valuetemp = sta.data.get(keytemp);
                for (int i = 0; i < 4; ++i) {
                    all[i] += valuetemp[i];
                }
            }
            sta.data.put("全国", all);
            return;
        }
        // 非默认日期
        for (File f : files) {
            if (LogFiles.dateCompare(date, f.getName().substring(0, 10))) {
                LogFiles.statisFile(f, sta);
                continue;
            }
            break;
        }

        // 各省数据统计到全国
        int[] all = { 0, 0, 0, 0 };
        for (String keytemp : sta.data.keySet()) {
            int[] valuetemp = sta.data.get(keytemp);
            for (int i = 0; i < 4; ++i) {
                all[i] += valuetemp[i];
            }
        }
        sta.data.put("全国", all);
    }

    // 统计某个日志文件的数据
    static void statisFile(File f, Statistic sta) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                // 忽略'/'开头的行
                if (line.startsWith("/")) {
                    continue;
                }
                LogFiles.statisLine(line, sta);
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // 处理日志文件中的一行
    static void statisLine(String line, Statistic sta) {
        String[] strs = line.split(" ");
        // 省份存在
        if (!sta.data.containsKey(strs[0])) {
            return;
        }
        // 8类日志项
        switch (strs[1]) {
        case "新增":
            if (strs[2].equals("感染患者")) {
                sta.data.get(strs[0])[0] += Integer.parseInt(strs[3].replace("人", ""));
                break;
            }
            if (strs[2].equals("疑似患者")) {
                sta.data.get(strs[0])[1] += Integer.parseInt(strs[3].replace("人", ""));
            }
            break;
        case "死亡":
            sta.data.get(strs[0])[0] -= Integer.parseInt(strs[2].replace("人", ""));
            sta.data.get(strs[0])[3] += Integer.parseInt(strs[2].replace("人", ""));
            break;
        case "治愈":
            sta.data.get(strs[0])[0] -= Integer.parseInt(strs[2].replace("人", ""));
            sta.data.get(strs[0])[2] += Integer.parseInt(strs[2].replace("人", ""));
            break;
        case "排除":
            sta.data.get(strs[0])[1] -= Integer.parseInt(strs[3].replace("人", ""));
            break;
        case "疑似患者":
            if (strs[2].equals("流入")) {
                sta.data.get(strs[0])[1] -= Integer.parseInt(strs[4].replace("人", ""));
                sta.data.get(strs[3])[1] += Integer.parseInt(strs[4].replace("人", ""));
                break;
            }
            if (strs[2].equals("确诊感染")) {
                sta.data.get(strs[0])[1] -= Integer.parseInt(strs[3].replace("人", ""));
                sta.data.get(strs[0])[0] += Integer.parseInt(strs[3].replace("人", ""));
            }
            break;
        case "感染患者":
            if (strs[2].equals("流入")) {
                sta.data.get(strs[0])[0] -= Integer.parseInt(strs[4].replace("人", ""));
                sta.data.get(strs[3])[0] += Integer.parseInt(strs[4].replace("人", ""));
            }
            break;
        default:
            break;
        }
    }

    // 比较两个YYYY-MM-DD日期字符串 若date0大于等于date1 则返回true
    static boolean dateCompare(String date0, String date1) {
        // 按年份月份日期依次比较时间前后
        if (Integer.parseInt(date0.substring(0, 4)) < Integer.parseInt(date0.substring(0, 4))) {
            return false;
        }
        if (Integer.parseInt(date0.substring(0, 4)) > Integer.parseInt(date1.substring(0, 4))) {
            return true;
        }
        if (Integer.parseInt(date0.substring(5, 7)) < Integer.parseInt(date1.substring(5, 7))) {
            return false;
        }
        if (Integer.parseInt(date0.substring(5, 7)) > Integer.parseInt(date1.substring(5, 7))) {
            return true;
        }
        if (Integer.parseInt(date0.substring(8, 10)) < Integer.parseInt(date1.substring(8, 10))) {
            return false;
        }
        if (Integer.parseInt(date0.substring(8, 10)) > Integer.parseInt(date1.substring(8, 10))) {
            return true;
        }
        return true;
    }
}
