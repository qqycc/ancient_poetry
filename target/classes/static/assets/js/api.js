function creationRanking(id) {
    //HTTP Method : GET
    //HTTP URL : 请求地址（服务端提供的API接口）
    $.get({
        url: "/analyze/author_count",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            //echarts图表对象，document当前页面
            var myChart = echarts.init(document.getElementById(id));
            //图表
            var options = {
                //图表的标题
                title: {
                    text: '古诗创作排行榜',
                    x:'center'
                },
                tooltip: {},
                //柱状图的提示信息
                legend: {
                    data: ['数量(首)']
                },
                //X轴的数据：作者
                xAxis: {
                    data: []
                },
                //Y轴的数据：创作的数量
                yAxis: {},
                series: [{
                    name: '创作数量',
                    type: 'bar',
                    data: []
                }]
            };

            //服务端发出请求，返回的数据data
            //List<AuthorCount>
            for (var i=0; i< data.length; i++) {
                var authorCount  = data[i];
                //给x轴动态添加作者
                options.xAxis.data.push(authorCount.author);
                options.series[0].data.push(authorCount.count);
            }
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {
        }
    });
}

function topTen(id) {
    $.get({
        url: "/analyze/top_ten",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            var myChart = echarts.init(document.getElementById(id));
            var options = {
                title: {
                    text: '排行榜Top10',
                    x:'center'
                },
                tooltip: {},
                legend: {
                    data: ['数量(首)']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [{
                    name: '创作数量',
                    type: 'bar',
                    data: []
                }]
            };

            for (var i=0; i< data.length; i++) {
                var authorCount  = data[i];
                //给x轴动态添加作者
                options.xAxis.data.push(authorCount.author);
                options.series[0].data.push(authorCount.count);
            }
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {
        }
    });
}

function cloudWorld(id) {
    $.get({
        url: "/analyze/word_cloud",
        dataType: "json",
        method: "get",
        success: function (data, status, xhr) {
            var myChart = echarts.init(document.getElementById(id));
            var options = {
                series: [{
                    type: 'wordCloud',
                    shape: 'cardioid',
                    left: 'center',
                    top: 'center',
                    width: '93%',
                    height: '93%',
                    right: null,
                    bottom: null,
                    sizeRange: [12,58],
                    rotationRange: [-90, 90],
                    rotationStep: 45,
                    gridSize: 9,
                    drawOutOfBound: false,
                    textStyle: {
                        normal: {
                            fontFamily: 'sans-serif',
                            fontWeight: 'bold',
                            color: function () {
                                //rgb(r,g,b)
                                return 'rgb(' + [
                                    Math.round(Math.random() * 200),
                                    Math.round(Math.random() * 200),
                                    Math.round(Math.random() * 220)
                                ].join(',') + ')';
                            }
                        },
                        emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    data: []
                }]
            };
            for (var i=0 ;i<data.length; i++) {
                var wordCount = data[i];
                //wordCount => 词 ： 词频
                options.series[0].data.push({
                    name: wordCount.word,
                    value: wordCount.count,
                    textStyle: {
                        normal: {},
                        emphasis: {}
                    }
                });
            }
            myChart.setOption(options, true);
        },
        error: function (xhr, status, error) {
        }
    });
}