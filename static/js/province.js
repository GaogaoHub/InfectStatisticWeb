var province = document.getElementById("echarts");
var pro_chart = echarts.init(province);
var app = {};
option = null;

var province_data=JSON.parse(window.localStorage.getItem('initdata'));
//var latest_data=province_data["data"][0];

// var date_list_add=[];//新增用日期
// var date_list=[];//累计用日期
// var con_list=[];//累计确诊
// var new_list=[];//新增确诊
// var cure_list=[];//治愈
// var dead_list=[];//死亡

// for(var i=province_data["data"].length-1;i>=0;i--){
//     if (i<=province_data["data"].length-2) {
//             date_list_add.push(province_data["data"][i]["date"]);
//             new_list.push(province_data["data"][i]["conNum"]-province_data["data"][i+1]["conNum"]);
//     }
//     if (province_data["data"][i]["deathNum"]=="") province_data["data"][i]["deathNum"]=0;
//     if (province_data["data"][i]["cureNum"]=="") province_data["data"][i]["cureNum"]=0;
//     date_list.push(province_data["data"][i]["date"]);
//     con_list.push(province_data["data"][i]["conNum"]);
//     cure_list.push(province_data["data"][i]["cureNum"]);
//     dead_list.push(province_data["data"][i]["deathNum"]);
// }
data = [//这些是静态数据
    ["1.27",116],["1.28",129],["1.29",135],["1.30",86],["1.31",73],["2.1",85],["2.2",73],["2.3",68],["2.4",92],["2.5",130],["2.6",245],["2.7",139],["2.8",115],["2.9",111],["2.10",309],["2.11",206],["2.12",137],["2.13",128],["2.14",85],["2.15",94],["2.16",71],["2.17",106],["2.18",84],["2.19",93],["2.20",85],["2.21",73],["2.22",83],["2.23",125],["2.24",107],["2.25",82]];

var dateList = data.map(function (item) {
    return item[0];
});
var valueList = data.map(function (item) {
    return item[1];
});

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
        type: 'line',
        showSymbol: false,
        data: valueList
    }]
};;
if (option && typeof option === "object") {
    pro_chart.setOption(option, true);
}