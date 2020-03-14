<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="javar.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	    LogFiles a = new LogFiles("D:\\logs");
	    Statistic b = new Statistic();
	    a.readFiles("lastdate", b);
	    String[] provincesort = { "全国", "安徽", "北京", "重庆", "福建", "甘肃", "广东", "广西", "贵州", "海南", "河北", "河南", "黑龙江",
	        "湖北", "湖南", "江西", "吉林", "江苏", "辽宁", "内蒙古", "宁夏", "青海", "山西", "山东", "陕西", "上海", "四川", "天津", "西藏", "新疆",
	        "云南", "浙江" };
	    for (int i = 0; i < provincesort.length; ++i) {
	%>

	<p><%=b.getdata(provincesort[i])%></p>

	<%
	    }
	%>

</body>
</html>