<%@page import="javar.BaseDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="../static/css/main.css" rel="stylesheet" />
<title>疫情统计可视化页面</title>
</head>
<body>
	<%
	    BaseDao dao = new BaseDao();
	    dao.createDateTable("lastdate");
	%>
	<sql:setDataSource var="asql" driver="com.mysql.jdbc.Driver"
		url="jdbc:mysql://localhost:3306/infectprovince" user="root"
		password="ggsqsjzcmd" />
	<sql:query dataSource="${asql}" var="resultSet">SELECT * FROM lastdate WHERE province='全国';</sql:query>
	
	<div class="container">
		<div class="left-group"></div>
		<div class="mid-group">
			<div class="title">
				<h2>疫情统计可视化页面</h2>
			</div>
			<c:forEach var="row" items="${resultSet.rows}">
			<div class="data">
				<div class="div-big">
					<div class="box">
						<div class="div-small">
							<p>现有确诊</p>
							<h3 class="nowSp"><c:out value="${row.ip}"/></h3>
							<p class="compare">
								昨日<span class="num">xxx</span>
							</p>
						</div>
						<div class="div-small">
							<p>累计确诊</p>
							<h3 class="nowSp">xxx</h3>
							<p class="compare">
								昨日<span class="num">xxx</span>
							</p>
						</div>
					</div>
					<div class="box">
						<div class="div-small">
							<p>现有疑似</p>
							<h3 class="nowSp"><c:out value="${row.sp}"/></h3>
							<p class="compare">
								昨日<span class="num">xxx</span>
							</p>
						</div>
						<div class="div-small">
							<p>累计治愈</p>
							<h3 class="nowSp"><c:out value="${row.cure}"/></h3>
							<p class="compare">
								昨日<span class="num">xxx</span>
							</p>
						</div>
					</div>
					<div class="box">
						<div class="div-small">
							<p>现有重症</p>
							<h3 class="nowSp">xxx</h3>
							<p class="compare">
								昨日<span class="num">xxx</span>
							</p>
						</div>
						<div class="div-small">
							<p>累计死亡</p>
							<h3 class="nowSp"><c:out value="${row.dead}"/></h3>
							<p class="compare">
								昨日<span class="num">xxx</span>
							</p>
						</div>
					</div>
				</div>
			</div>
			</c:forEach>
			<div class="radio">
				<input type="radio" id="tab-1" name="show" onclick="change_to_con()"
					checked /> <input type="radio" id="tab-2" name="show"
					onclick="change_to_now()" />
				<div class="tab">
					<label for="tab-1">累计确诊</label> <label for="tab-2">现存确诊</label>
				</div>
			</div>
			<div id="echarts" class="map"></div>
		</div>
		<div class="right-group"></div>
	</div>
	<script src="../static/js/controller.js"></script>
</body>
</html>