<%@page import="javar.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="main.css" type="text/css" rel="stylesheet" />
<title>疫情统计可视化页面</title>
</head>
<body>
	<%
	    BaseDao dao = new BaseDao();
	    dao.createTables("D://logs");
	%>
	
	<% 
	    ProvinceByDate pbd=dao.searchProvinceByDate("全国");
	    int[] tmp=pbd.getData("2020-02-02");
	%>

	<div class="container">
		<div class="left-group"></div>
		<div class="mid-group">
			<div class="title">
				<h2>疫情统计可视化页面</h2>
			</div>
				<div class="data">
					<div class="div-big">
						<div class="box">
							<div class="div-small">
								<p>现有确诊</p>
								<h3 class="nowSp">
									<% out.write(Integer.toString(tmp[1])); %>
								</h3>
								<p class="compare">
									昨日<span class="num">xxx</span>
								</p>
							</div>
							<div class="div-small">
								<p>累计确诊</p>
								<h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp[0])); %>
								</h3>
								<p class="compare">
									昨日<span class="num">xxx</span>
								</p>
							</div>
						</div>
						<div class="box">
							<div class="div-small">
								<p>现有疑似</p>
								<h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp[2])); %>
								</h3>
								<p class="compare">
									昨日<span class="num">xxx</span>
								</p>
							</div>
							<div class="div-small">
								<p>累计治愈</p>
								<h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp[3])); %>
								</h3>
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
								<h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp[4])); %>
								</h3>
								<p class="compare">
									昨日<span class="num">xxx</span>
								</p>
							</div>
						</div>
					</div>
				</div>
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