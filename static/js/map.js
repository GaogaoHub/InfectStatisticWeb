var china = document.getElementById("echarts");
var china_chart = echarts.init(china);

var data = JSON.parse(window.localStorage.getItem('chinadata'));
var ccon_list = [];//累计数据
var cnow_list = [];//现存数据
for(var i=0;i<data["data"].length;i++){
    var temp = {
        "name":data["data"][i]["name"],
        "value":data["data"][i]["con_value"]
    }//构造符合echarts地图date的键值对
    var temp2 = {
        "name":data["data"][i]["name"],
        "value":data["data"][i]["now_value"]
    }
    ccon_list.push(temp);
    cnow_list.push(temp2);
}
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
            {min: 1000,color: '#8B4789 '},
            {min: 600, max: 1000,color: '  	#CD69C9'},
            {min: 200, max: 600 ,color: '	#CD96CD'},
            {min: 50, max: 300, color: ' #FFE1FF'},
            {max: 50, color: '#FFF0F5'}
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
                show: true,
                color: 'rgb(249, 249, 249)'
            },
            data: 
                <％out.write(雨欣儿给的函数)％>
                {name: '北京',value: 5},
                {name: '天津',value: Math.round(Math.random()*2000)},
                {name: '上海',value: Math.round(Math.random()*2000)},
                {name: '重庆',value: Math.round(Math.random()*2000)},
                {name: '河北',value: 0},
                {name: '河南',value: Math.round(Math.random()*2000)},
                {name: '云南',value: 123},
                {name: '辽宁',value: 305},
                {name: '黑龙江',value: Math.round(Math.random()*2000)},
                {name: '湖南',value: 200},
                {name: '安徽',value: Math.round(Math.random()*2000)},
                {name: '山东',value: Math.round(Math.random()*2000)},
                {name: '新疆',value: Math.round(Math.random()*2000)},
                {name: '江苏',value: Math.round(Math.random()*2000)},
                {name: '浙江',value: Math.round(Math.random()*2000)},
                {name: '江西',value: Math.round(Math.random()*2000)},
                {name: '湖北',value: Math.round(Math.random()*2000)},
                {name: '广西',value: Math.round(Math.random()*2000)},
                {name: '甘肃',value: Math.round(Math.random()*2000)},
                {name: '山西',value: Math.round(Math.random()*2000)},
                {name: '内蒙古',value: Math.round(Math.random()*2000)},
                {name: '陕西',value: Math.round(Math.random()*2000)},
                {name: '吉林',value: Math.round(Math.random()*2000)},
                {name: '福建',value: Math.round(Math.random()*2000)},
                {name: '贵州',value: Math.round(Math.random()*2000)},
                {name: '广东',value: Math.round(Math.random()*2000)},
                {name: '青海',value: Math.round(Math.random()*2000)},
                {name: '西藏',value: Math.round(Math.random()*2000)},
                {name: '四川',value: Math.round(Math.random()*2000)},
                {name: '宁夏',value: Math.round(Math.random()*2000)},
                {name: '海南',value: Math.round(Math.random()*2000)},
                {name: '台湾',value: Math.round(Math.random()*2000)},
                {name: '香港',value: Math.round(Math.random()*2000)},
                {name: '澳门',value: Math.round(Math.random()*2000)}
            
        }
    ]
};;
china_chart.setOption(china_option, true);
china_chart.on("click",function(e) {
    $.ajax({
        type:"POST",
        url: "/post_data",
        data: JSON.stringify(e.name),
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json',
        success: function(data) {
            localStorage.clear();
//            console.log(data);
            window.localStorage.setItem('initdata',JSON.stringify(data));
            location.href = 'province?index='+e.name;
        },
        error: function(xhr, type) {
        }
    });
})

