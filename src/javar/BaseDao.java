package javar;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import java.sql.ResultSet;

public class BaseDao {
    // 数据库连接
    private static final String URL = "jdbc:mysql://localhost:3306/infectprovince";
    // 驱动
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    // 用户名
    private static final String USRE = "root";
    // 用户密码
    private static final String PWD = "ggsqsjzcmd";

    private static final String[] PROVINCESORT_STRINGS = { "全国", "安徽", "北京", "重庆", "福建", "甘肃", "广东", "广西", "贵州", "海南",
        "河北", "河南", "黑龙江", "湖北", "湖南", "江西", "吉林", "江苏", "辽宁", "内蒙古", "宁夏", "青海", "山西", "山东", "陕西", "上海", "四川", "天津",
        "西藏", "新疆", "云南", "浙江" };

    /**
     * @throws Exception 获取数据库连接
     */
    Connection conn = null;
    String sql = null;
    Statement s = null;
    ResultSet rs = null;

    private void getConnection() throws Exception {
        if (conn == null) {
            // 1 加载驱动类
            Class.forName(DRIVER);
            // 2 获取数据库连接
            conn = DriverManager.getConnection(URL, USRE, PWD);
        }
        s = conn.createStatement();
    }

    private void closeConnection() throws Exception {
        s.close();
        conn.close();
        conn = null;
    }

    // 判断一张表是否存在
    Boolean existDateTable(String date) throws Exception {
        getConnection();
        ResultSet rs = conn.getMetaData().getTables(null, null, date, null);
        if (rs.next()) {
            closeConnection();
            return true;
        }
        closeConnection();
        return false;
    }

    // 根据传入的Statistic 建表 date
    void createDateTable(String date, Statistic sta) throws Exception {
        // 建表
        getConnection();
        sql = "CREATE TABLE `" + date + "` (\r\n" + "  `province` varchar(20) NOT NULL DEFAULT '全国',\r\n"
            + "  `newip` int(10) unsigned DEFAULT '0',\r\n" + "  `allip` int(10) unsigned DEFAULT '0',\r\n"
            + "  `nowip` int(10) unsigned DEFAULT '0',\r\n" + "  `sp` int(10) unsigned DEFAULT '0',\r\n"
            + "  `cure` int(10) unsigned DEFAULT '0',\r\n" + "  `dead` int(10) unsigned DEFAULT '0',\r\n"
            + "  PRIMARY KEY (`province`)\r\n" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        s.executeUpdate(sql);

        for (String str : PROVINCESORT_STRINGS) {
            int[] temp = sta.getdata(str);
            int allip = temp[0] + temp[2] + temp[3];
            sql = "INSERT INTO `" + date + "` (`province`, `newip`, `allip`, `nowip`, `sp`, `cure`, `dead`) VALUES ('"
                + str + "', '" + temp[4] + "', '" + allip + "', '" + temp[0] + "', '" + temp[1] + "', '" + temp[2]
                + "', '" + temp[3] + "')";
            s.executeUpdate(sql);
        }
        closeConnection();
        return;
    }

    // 读取全部日志 创建以日期为单位的数个疫情统计表 以及以省份为单位的数个疫情统计表
    public void createTables(String path) throws Exception {

        // 数据读取
        LogFiles logs = new LogFiles(path);
        Statistic sta = new Statistic();
        String tableName;

        // 建省份表
        createProvinceTables();

        for (File f : logs.files) {
            tableName = f.getName().substring(0, 10);
            LogFiles.statisFile(f, sta);
            if (existDateTable(tableName)) {
                continue;
            }
            createDateTable(tableName, sta);
            updateProvinceTables(tableName, sta);
        }
        return;
    }

    void createProvinceTables() throws Exception {
        for (String str : PROVINCESORT_STRINGS) {
            if (existDateTable(str)) {
                return;
            }
            getConnection();
            sql = "CREATE TABLE `" + str + "` (\r\n" + "  `date` varchar(20) NOT NULL DEFAULT '1997-01-01',\r\n"
                + "  `newip` int(10) unsigned DEFAULT '0',\r\n" + "  `allip` int(10) unsigned DEFAULT '0',\r\n"
                + "  `nowip` int(10) unsigned DEFAULT '0',\r\n" + "  `sp` int(10) unsigned DEFAULT '0',\r\n"
                + "  `cure` int(10) unsigned DEFAULT '0',\r\n" + "  `dead` int(10) unsigned DEFAULT '0',\r\n"
                + "  PRIMARY KEY (`date`)\r\n" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            s.executeUpdate(sql);
            closeConnection();
        }
        return;
    }

    // 更新每张province表，插入date日期的数据
    void updateProvinceTables(String date, Statistic sta) throws Exception {
        getConnection();
        for (String str : PROVINCESORT_STRINGS) {
            int[] temp = sta.getdata(str);
            int allip = temp[0] + temp[2] + temp[3];
            sql = "INSERT INTO `" + str + "` (`date`, `newip`, `allip`, `nowip`, `sp`, `cure`, `dead`) VALUES ('" + date
                + "', '" + temp[4] + "', '" + allip + "', '" + temp[0] + "', '" + temp[1] + "', '" + temp[2] + "', '"
                + temp[3] + "')";
            s.executeUpdate(sql);
        }
        closeConnection();
        return;
    }

    public ProvinceByDate searchProvinceByDate(String province) throws Exception {
        String stmp = province.replace("\"", "");

        ProvinceByDate pbd = new ProvinceByDate(stmp);

        getConnection();
        sql = "SELECT * FROM `" + stmp + "`";
        rs = s.executeQuery(sql);

        rs.beforeFirst();
        while (rs.next()) {
            String date = rs.getString("date");
            int[] tmp = { 0, 0, 0, 0, 0 };
            tmp[0] = rs.getInt("allip");
            tmp[1] = rs.getInt("nowip");
            tmp[2] = rs.getInt("sp");
            tmp[3] = rs.getInt("cure");
            tmp[4] = rs.getInt("dead");
            pbd.add(date, tmp);
        }

        closeConnection();
        return pbd;
    }

    public String searchNowipByProvince(String date) throws Exception {

        getConnection();
        sql = "SELECT\r\n" + "nowip,\r\n" + "province\r\n" + "FROM `" + date + "`";
        rs = s.executeQuery(sql);
        Map<String, Integer> map = new HashMap<String, Integer>();

        rs.beforeFirst();
        while (rs.next()) {
            String province = rs.getString("province");
            Integer nowip = rs.getInt("nowip");
            map.put(province, nowip);
        }
        closeConnection();
        map.remove("全国");
        String result = BaseDao.intMaptoJson(map);
        return result;
    }

    public String searchProvincedateByDate(String province) throws Exception {
        getConnection();
        String stmp = province.replace("\"", "");
        sql = "SELECT * FROM `" + stmp + "`";
        rs = s.executeQuery(sql);
        Map<String, int[]> map = new HashMap<String, int[]>();

        rs.beforeFirst();
        while (rs.next()) {
            String date = rs.getString("date");
            int[] tmp = { 0, 0, 0, 0 };
            tmp[0] = rs.getInt("newip");
            tmp[1] = rs.getInt("allip");
            tmp[2] = rs.getInt("cure");
            tmp[3] = rs.getInt("dead");
            map.put(date, tmp);
        }
        closeConnection();

        String result = BaseDao.intsMaptoJson(map);
        return result;
    }

    static String intMaptoJson(Map<String, Integer> map) {
        String result = "[";
        String stmp;
        for (String key : map.keySet()) {
            stmp = "{ \"name\": '" + key + "',\"value\": " + map.get(key).toString() + "}";
            result = result + stmp + ",";
        }
        result = result.substring(0, result.length() - 1);
        result += "]";
        return result;
    }

    static String intsMaptoJson(Map<String, int[]> map) {
        String result = "[";
        String stmp;

        for (String key : map.keySet()) {
            stmp = "{ \"date\": '" + key + "',\"newlist\": " + Integer.toString((map.get(key))[0]) + ",\"conlist\": "
                + Integer.toString((map.get(key))[1]) + ",\"curelist\": " + Integer.toString((map.get(key))[2])
                + ",\"deadlist\": " + Integer.toString((map.get(key))[3]) + "}";
            result = result + stmp + ",";
        }
        result = result.substring(0, result.length() - 1);
        result += "]";
        return result;
    }

    /*
     * //按日期查询 private ResultSet selectDateView(String date) throws Exception {
     * //若表不存在返回null if (!existDateTable(date)) { return null; } getConnection();
     * sql="SELECT * FROM"+date; rs=s.executeQuery(sql); return rs; }
     * 
     * //按省份查询 private ResultSet selectProvinceView(String province) {
     * 
     * return rs; }
     */
}
