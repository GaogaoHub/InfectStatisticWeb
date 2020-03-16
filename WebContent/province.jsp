<%@page import="javar.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <link href="province.css" rel="stylesheet"/>
    <title>疫情统计情况</title>
</head>
<body>

    <%
        BaseDao dao = new BaseDao();
        dao.createTables("D://logs");
    %>
    <%
            String test = request.getParameter("test");
            ProvinceByDate pbd=dao.searchProvinceByDate(test);
            String res=dao.searchProvincedateByDate(test);
            int[] tmp=pbd.getData("2020-02-02");

    %>
    <div class="container">
        <div class="left-group"></div>
        <div class="mid-group">
            <div class="title">
                <h2>省份疫情统计可视化页面</h2>                
                <p id="province">省份</p>
            </div>
            <div class="data">
                <div class="divLeft">
                    <h4>现有确诊</h4>
                    <h3 class="nowSp">
                        <% out.write(Integer.toString(tmp[1])); %>
                    </h3>
                </div>
                <div class="divLeft">
                    <h4>累计确诊</h4>
                    <h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp[0])); %>
                                </h3>
                </div>
                <div class="divLeft">
                    <h4>累计治愈</h4>
                    <h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp[3])); %>
                                </h3>
                </div>
                <div class="divLeft">
                    <h4>累计死亡</h4>
                    <h3 class="nowSp">
                                    <% out.write(Integer.toString(tmp[4])); %>
                                </h3>
                </div>
            </div>
            <div id="echarts" class="echarts"></div>
            <div class="radio">
                <input type="radio" id="tab-1" name="show" onclick="change_to_sum()" checked/>
                <input type="radio" id="tab-2" name="show" onclick="change_to_procon()"/>
                <input type="radio" id="tab-3" name="show" onclick="change_to_cure_dead()"/>
                <div class="tab">
                    <label for="tab-1">新增确诊趋势</label>
                    <label for="tab-2">累计确诊趋势</label>
                    <label for="tab-3">累计治愈/死亡</label>
                    <div class="back"><a href="main.jsp" class="myButton">返回全国</a></div>
                </div>
            </div>
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
    <script >
    var province = document.getElementById("echarts");
    var pro_chart = echarts.init(province);
    var app = {};
    option = null;


    function get_url_param(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var param_name = window.location.search.substr(1).match(reg);
            if(param_name != null){
                return decodeURIComponent(param_name[2]); //decodeURIComponent 处理中文乱码
            }
            return null;
    }
    
    var provinceName=get_url_param('test');
    document.getElementById("province").innerHTML = provinceName;

    var p=JSON.parse("$res");
  //var latest_data=p[0];
   var date_list=[];//累计用日期
   var con_list=[];//累计确诊
   var new_list=[];//新增确诊
   var cure_list=[];//治愈
   var dead_list=[];//死亡

  function getJsonObjLength(jsonObj) {
      var Length = 0;
  for (var item in jsonObj) {
         Length++;
      }
     return Length;
  }
  var length=getJsonObjLength(p);
   for(var i=length-1;i>=0;i--){
      if (i<=length-2) {
              date_list_add.push(p[i]["date"]);
       }
      new_list.push(p[i]["newlist"]);
      date_list.push(p[i]["date"]);
      con_list.push(p[i]["conlist"]);
      cure_list.push(p[i]["curelist"]);
      dead_list.push(p[i]["deathlist"]);
   }
    /*data = [//这些是静态数据
        ["1.27",116],["1.28",129],["1.29",135],["1.30",86],["1.31",73],["2.1",85],["2.2",73],["2.3",68],["2.4",92],["2.5",130],["2.6",245],["2.7",139],["2.8",115],["2.9",111],["2.10",309],["2.11",206],["2.12",137],["2.13",128],["2.14",85],["2.15",94],["2.16",71],["2.17",106],["2.18",84],["2.19",93],["2.20",85],["2.21",73],["2.22",83],["2.23",125],["2.24",107],["2.25",82]];

    var dateList = data.map(function (item) {
        return item[0];
    });
    var valueList = data.map(function (item) {
        return item[1];
    });
    */
    option = {
        visualMap: [{
            show: false,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 400
        }],
        title: [{
            left: 'left',
            text: '新增确诊趋势图',
        }],
        color:['#DC143C','#A52A2A','#10aeb5','#000000'],
        legend: {
            show:false,
            data: ['新增确诊', '累计确诊', '累计治愈', '累计死亡'],
            selected:{
                '新增确诊':true,
                '累计确诊':false,
                '累计治愈':false,
                '累计死亡':false
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [{
            data: dateList
        }],
        yAxis: [{
            splitLine: {show: false}
        }],
        grid: [{
            bottom: '10%'
        }],
        series: [{
            showLegendSymbol: false,
            name:'新增确诊',
            type: 'line',

            showSymbol: false,
            data: new_list
        },
        {
            showLegendSymbol: false,
            name:'累计确诊',
            type: 'line',
            showSymbol: false,
            data: con_list
        },
        {
            showLegendSymbol: false,
            name:'累计治愈',
            type: 'line',
            showSymbol: false,
            data: cure_list
        },
        {
            showLegendSymbol: false,
            name:'累计死亡',
            type: 'line',
            showSymbol: false,
            data: dead_list
        }
        ]
    };;
    if (option && typeof option === "object") {
        pro_chart.setOption(option, true);
    }
    </script>
</body>