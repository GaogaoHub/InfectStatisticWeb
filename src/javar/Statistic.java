package javar;
import java.util.HashMap;
import java.util.Map;

public class Statistic {
    Map<String, int[]> data;

    public Statistic() {
        data = new HashMap<String, int[]>();
        data.put("全国", new int[] { 0, 0, 0, 0 });
        data.put("安徽", new int[] { 0, 0, 0, 0 });
        data.put("北京", new int[] { 0, 0, 0, 0 });
        data.put("重庆", new int[] { 0, 0, 0, 0 });
        data.put("福建", new int[] { 0, 0, 0, 0 });
        data.put("甘肃", new int[] { 0, 0, 0, 0 });
        data.put("广东", new int[] { 0, 0, 0, 0 });
        data.put("广西", new int[] { 0, 0, 0, 0 });
        data.put("贵州", new int[] { 0, 0, 0, 0 });
        data.put("海南", new int[] { 0, 0, 0, 0 });
        data.put("河北", new int[] { 0, 0, 0, 0 });
        data.put("河南", new int[] { 0, 0, 0, 0 });
        data.put("黑龙江", new int[] { 0, 0, 0, 0 });
        data.put("湖北", new int[] { 0, 0, 0, 0 });
        data.put("湖南", new int[] { 0, 0, 0, 0 });
        data.put("江西", new int[] { 0, 0, 0, 0 });
        data.put("吉林", new int[] { 0, 0, 0, 0 });
        data.put("江苏", new int[] { 0, 0, 0, 0 });
        data.put("辽宁", new int[] { 0, 0, 0, 0 });
        data.put("内蒙古", new int[] { 0, 0, 0, 0 });
        data.put("宁夏", new int[] { 0, 0, 0, 0 });
        data.put("青海", new int[] { 0, 0, 0, 0 });
        data.put("山西", new int[] { 0, 0, 0, 0 });
        data.put("山东", new int[] { 0, 0, 0, 0 });
        data.put("陕西", new int[] { 0, 0, 0, 0 });
        data.put("上海", new int[] { 0, 0, 0, 0 });
        data.put("四川", new int[] { 0, 0, 0, 0 });
        data.put("天津", new int[] { 0, 0, 0, 0 });
        data.put("西藏", new int[] { 0, 0, 0, 0 });
        data.put("新疆", new int[] { 0, 0, 0, 0 });
        data.put("云南", new int[] { 0, 0, 0, 0 });
        data.put("浙江", new int[] { 0, 0, 0, 0 });
    }

    public String getdata(String province) {
        int[] valuetemp = data.get(province);
        String outline = province;
        outline = outline + " 感染患者" + valuetemp[0] + "人";
        outline = outline + " 疑似患者" + valuetemp[1] + "人";
        outline = outline + " 治愈" + valuetemp[2] + "人";
        outline = outline + " 死亡" + valuetemp[3] + "人";
        return outline;
    }
}
