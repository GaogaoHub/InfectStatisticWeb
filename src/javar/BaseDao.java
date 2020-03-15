package javar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

    Boolean existDateTable(String date) throws Exception {
        getConnection();
        ResultSet rs = conn.getMetaData().getTables(null, null, date, null);
        if (rs.next()) {
            s.close();
            conn.close();
            return true;
        }
        s.close();
        conn.close();
        conn = null;
        return false;
    }

    // 创建截止某时期的疫情统计表
    public void createDateTable(String date) throws Exception {
        // 表是否已经存在
        if (existDateTable(date)) {
            return;
        }

        // 数据读取
        LogFiles logs = new LogFiles("D:\\logs");
        Statistic sta = new Statistic();
        String[] provincesort = { "全国", "安徽", "北京", "重庆", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河北", "河南", "黑龙江", "湖北",
            "湖南", "江西", "吉林", "江苏", "辽宁", "内蒙古", "宁夏", "青海", "山西", "山东", "陕西", "上海", "四川", "天津", "西藏", "新疆", "云南",
            "浙江" };
        logs.readFiles(date, sta);

        // 建表
        getConnection();
        sql = "CREATE TABLE `" + date + "` (\r\n" + "  `num` int(10) unsigned NOT NULL AUTO_INCREMENT,\r\n"
            + "  `province` varchar(10) CHARACTER SET utf8mb4 DEFAULT '全国',\r\n"
            + "  `allip` int(10) unsigned DEFAULT '0',\r\n" + "  `nowip` int(10) unsigned DEFAULT '0',\r\n"
            + "  `sp` int(10) unsigned DEFAULT '0',\r\n" + "  `cure` int(10) unsigned DEFAULT '0',\r\n"
            + "  `dead` int(10) unsigned DEFAULT '0',\r\n" + "  PRIMARY KEY (`num`)\r\n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        s.executeUpdate(sql);

        for (int i = 0; i < provincesort.length; ++i) {
            int[] temp = sta.getdata(provincesort[i]);
            int allip = temp[0] + temp[2] + temp[3];
            sql = "INSERT INTO " + date + "(province,allip,nowip,sp,cure,dead) VALUES ('" + provincesort[i] + "',"
                + allip + ","+ temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + ")";
            s.executeUpdate(sql);
        }
        s.close();
        conn.close();
        conn = null;
        return;
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
