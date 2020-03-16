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
        int[] tmp0=pbd.getData("2020-02-01");
	    int[] tmp1=pbd.getData("2020-02-02");
	    for(int i=0;i<5;++i){
	        tmp0[i]=tmp1[i]-tmp0[i];
	    }
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
									<% out.write(Integer.toString(tmp1[1])); %>
								</h3>
								<p class="compare">
									昨日
									<span class="num">
									   <% 
									        if(tmp0[1]>0){
									            out.write("+");
									        }
									    	out.write(Integer.toString(tmp0[1])); 
									    %>
									</span>
								</p>
							</div>
							<div class="div-small">
								<p>累计确诊</p>
								<h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp1[0])); %>
								</h3>
								<p class="compare">
									昨日<span class="num"><% 
									    if(tmp0[0]>0){
                                            out.write("+");
                                        }
									out.write(Integer.toString(tmp0[0])); %></span>
								</p>
							</div>
						</div>
						<div class="box">
							<div class="div-small">
								<p>现有疑似</p>
								<h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp1[2])); %>
								</h3>
								<p class="compare">
									昨日<span class="num"><%
									    if(tmp0[2]>0){
                                            out.write("+");
                                        }
									out.write(Integer.toString(tmp0[2])); %></span>
								</p>
							</div>
							<div class="div-small">
								<p>累计治愈</p>
								<h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp1[3])); %>
								</h3>
								<p class="compare">
									昨日<span class="num"><%
									    if(tmp0[3]>0){
                                            out.write("+");
                                        }
									out.write(Integer.toString(tmp0[3])); %></span>
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
                                    <% out.write(Integer.toString(tmp1[4])); %>
								</h3>
								<p class="compare">
									昨日<span class="num"><%
									    if(tmp0[4]>0){
                                            out.write("+");
                                        }
									out.write(Integer.toString(tmp0[4])); %></span>
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
			<form name="frmApp" action="province.jsp" id="frmAppId" mothed="post">
                <input id="test" type="hidden" name="test">
            </form>
		</div>
		<div class="right-group"></div>
	</div>
	
	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts-gl/dist/echarts-gl.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts-stat/dist/ecStat.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/dist/extension/dataTool.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/map/js/china.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/map/js/world.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts/dist/extension/bmap.min.js"></script>
	<script>
	var china = document.getElementById("echarts");
	var china_chart = echarts.init(china);

	var app = {};
	var china_option = {//option
	    title: {
	        text: '中国疫情情况图',
	        left: 'center'
	    },
	    tooltip: {
	        trigger: 'item'
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['确诊人数']
	    },
	    visualMap: {
	        type: 'piecewise',
	        pieces: [
	            {min: 1000,color: '#5A1D07 '},
	            {min: 600, max: 1000,color: '   #A7340E'},
	            {min: 200, max: 600 ,color: '   #C94518'},
	            {min: 50, max: 300, color: ' #F28E6C'},
	            {max: 50, color: '#F8C1AF'}
	        ],
	        color: ['#E0022B', '#E09107', '#A3E00B']
	    },
	    roamController: {
	        show: true,
	        left: 'right',
	    mapTypeControl: {
	            'china': true
	        }
	    },
	    series: [
	        {
	            name: '确诊人数',
	            type: 'map',
	            mapType: 'china',
	            roam: false,
	            label: {
	                show: false,
	                color: 'rgb(249, 249, 249)'
	            },
	            data: 
	                <% out.write(dao.searchNowipByProvince("2020-02-02"));%>  
	        }
	    ]
	};;
	
	
	if (china_option && typeof china_option === "object") {
	    china_chart.setOption(china_option, true);
	}
	
	china_chart.on("click",function(e) {//跳转
		   //console.log(e);
		    var tmp=setItemValue(JSON.stringify(e.name));
		    submit();
		    location.href='province.jsp?test='+e.name;//要跳转的页面地址		   //window.open()//打开另一个
		})

		function setItemValue(tmp){
		    document.getElementById("test").value = tmp;  // 将JS变量值存储到隐藏控件中
		}

		function submit(){
		    var frm = document.getElementById("frmAppId"); // 获取表单
		    frm.submit(); // 对表单进行提交
		}
	</script>

</body>
</html>