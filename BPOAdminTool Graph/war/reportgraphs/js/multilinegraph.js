/*
 * This js library is for generating a multiseries line graph using d3.
 * It's necessary to link d3 files first before importing this in your html file.
 * If your data for x-axis is not labelled time, you have to configure the code below
 * to reflect your data. Also if your data for x-axis is not in the format of "HH:mm:SS",
 * do the necessary changes on the code below. Likewise, if the data for y=axis is not of
 * discrete numbers, do the necessary change on the code below too.
 * 
 * @author rfuentes
 */

var defaultMultiSeriesLineGraphConfig = {
    container: "svg",
    jsonStr: $.parseJSON('[{"time": "10:59:16", "waiting": 74, "processing": 0},{"time": "10:59:17", "waiting": 68, "processing": 0},{"time": "10:59:18", "waiting": 70, "processing": 0}]'),
    margin: {top: 20, right: 0, bottom: 100, left: 50},
    padding: {top: 0, right: 0, bottom: 0, left: 0},
    outerWidth: 900,
    outerHeight: 600,
    innerWidth: function() { return this.outerWidth - this.margin.left - this.margin.right },
    innerHeight: function() { return this.outerHeight - this.margin.top - this.margin.bottom },
    width: function() { return this.innerWidth() - this.padding.left - this.padding.right },
    height: function() { return this.innerHeight() - this.padding.top - this.padding.bottom }
};

//console.log(defaultMultiSeriesLineGraphConfig.width());

function generateMultiSeriesLineGraph(config){
    var margin = config.margin,
        padding = config.padding,
        outerWidth = config.outerWidth,
        outerHeight = config.outerHeight,
        innerWidth = config.innerWidth(),
        innerHeight = config.innerHeight(),
        width = config.width(),
        height = config.height(),
        data = config.jsonStr;

    var parseTime = d3.time.format("%X").parse;

    var x = d3.time.scale().rangeRound([0, width]);
    var y = d3.scale.linear().range([height, 0]);

    var color = d3.scale.category10();

    var xAxis = d3.svg.axis().scale(x).orient("bottom").tickFormat(d3.time.format("%X")).ticks(5);
    var yAxis = d3.svg.axis().scale(y).orient("left").tickFormat(d3.format('.0f')).ticks(5);

    var line = d3.svg.line().x(function(d) { return x(d.time); })
                            .y(function(d) { return y(d.items); })

    var svg = d3.select(config.container)
                .append("svg")
                .attr("width", width)
                .attr("height", height)
                .attr('viewBox','0 0 '+ outerWidth +' '+outerHeight)
                //.attr('preserveAspectRatio','xMinYMin')
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    color.domain(d3.keys(data[0]).filter(function(key) { return key !== "time"; }));

       data.forEach(function(d) {
        d.time = parseTime(d.time);
    });

    var categories = color.domain().map(function(name) {
        return {
            name: name,
            values: data.map(function(d) {
                return {
                    time: d.time, 
                      items: +d[name]
                  };
            })
        };
    });

    x.domain(d3.extent(data, function(d) { return d.time; })); // extent = highest and lowest points, domain is data, range is bouding box
    
    var yMax = d3.max(categories, function(c) { return d3.max(c.values, function(v) { return v.items; }); });
    
    y.domain([
        0,
        yMax
    ]);
    
    
    if( yMax < 5) yAxis.ticks(yMax);

    svg.append("g").attr("class", "x axis")
                   .attr("transform", "translate(0," + height + ")")
                   .call(xAxis);

    svg.append("g").attr("class", "y axis")
                   .call(yAxis)
       .append("text").attr("transform", "rotate(-90)")
                      .attr("y", 6)
                      .attr("x", -10)
                      .attr("dy", ".71em")
                      .style("text-anchor", "end")
                      .text("Items");
    

    //draw lines
    var trends = svg.selectAll(".trend").data(categories).enter().append("g").attr("class", "trend");

    trends.append("path").attr("class", "line")
                         //.style("pointer-events", "none") // Stop line interferring with cursor
                         .attr("d", function(d) { 
                            return line(d.values)
                         })
                         .style("stroke", function(d) {
                             return color(d.name);
                         });

    //draw legend
    var legendSpace = 50 / categories.length;

    trends.append("rect").attr("width", 10)
                        .attr("height", 10)
                        .attr("x", width + (margin.right/20))
                        .attr("y", function (d, i) { return (legendSpace)+i*(legendSpace) - 8; })  // spacing
                        .attr("fill", function(d) {
                            return color(d.name);
                          })
                          .attr("class", "legend-box");

    trends.append("text").attr("x", width + (margin.right/20) + 15) 
                         .attr("y", function (d, i) { return (legendSpace)+i*(legendSpace); })  // (return (11.25/2 =) 5.625) + i * (5.625)
                         .text(function(d) { return d.name; });

    var yAxisGrid = yAxis.ticks(( yMax < 5)? yMax : 5)
                         .tickSize(width, 0)
                         .tickFormat("")
                         .orient("right");

    var xAxisGrid = xAxis.ticks(5)
                         .tickSize(-height, 0)
                         .tickFormat("")
                         .orient("top");

    svg.append("g").attr("class", "grid").call(yAxisGrid);

    svg.append("g").attr("class", "grid").call(xAxisGrid);
}


function updateMultiSeriesLineGraph(config){
    var margin = config.margin,
        padding = config.padding,
        outerWidth = config.outerWidth,
        outerHeight = config.outerHeight,
        innerWidth = config.innerWidth(),
        innerHeight = config.innerHeight(),
        width = config.width(),
        height = config.height(),
        data = config.jsonStr;

    var parseTime = d3.time.format("%X").parse;

    var x = d3.time.scale().rangeRound([0, width]);
    var y = d3.scale.linear().range([height, 0]);

    var color = d3.scale.category10();

    var xAxis = d3.svg.axis().scale(x).orient("bottom").tickFormat(d3.time.format("%X")).ticks(5);
    var yAxis = d3.svg.axis().scale(y).orient("left").tickFormat(d3.format('.0f')).ticks(5);

    var line = d3.svg.line().x(function(d) { return x(d.time); })
                            .y(function(d) { return y(d.items); })

    d3.select(config.container)
                .select("svg").remove();

    var svg = d3.select(config.container)
                .append("svg")
                .attr("width", width)
                .attr("height", height)
                .attr('viewBox','0 0 '+ outerWidth +' '+outerHeight)
                //.attr('preserveAspectRatio','xMinYMin')
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    color.domain(d3.keys(data[0]).filter(function(key) { return key !== "time"; }));

       data.forEach(function(d) {
        d.time = parseTime(d.time);
    });

    var categories = color.domain().map(function(name) {
        return {
            name: name,
            values: data.map(function(d) {
                return {
                    time: d.time, 
                      items: +d[name]
                  };
            })
        };
    });

    x.domain(d3.extent(data, function(d) { return d.time; })); // extent = highest and lowest points, domain is data, range is bouding box
    
    var yMax = d3.max(categories, function(c) { return d3.max(c.values, function(v) { return v.items; }); });
    
    y.domain([
        0,
        yMax
    ]);
    
    
    if( yMax < 5) yAxis.ticks(yMax);

    svg.append("g").attr("class", "x axis")
                   .attr("transform", "translate(0," + height + ")")
                   .call(xAxis);

    svg.append("g").attr("class", "y axis")
                   .call(yAxis)
       .append("text").attr("transform", "rotate(-90)")
                      .attr("y", 6)
                      .attr("x", -10)
                      .attr("dy", ".71em")
                      .style("text-anchor", "end")
                      .text("Items");
    

    //draw lines
    var trends = svg.selectAll(".trend").data(categories).enter().append("g").attr("class", "trend");

    trends.append("path").attr("class", "line")
                         //.style("pointer-events", "none") // Stop line interferring with cursor
                         .attr("d", function(d) { 
                            return line(d.values)
                         })
                         .style("stroke", function(d) {
                             return color(d.name);
                         });

    //draw legend
    var legendSpace = 50 / categories.length;

    trends.append("rect").attr("width", 10)
                        .attr("height", 10)
                        .attr("x", width + (margin.right/20))
                        .attr("y", function (d, i) { return (legendSpace)+i*(legendSpace) - 8; })  // spacing
                        .attr("fill", function(d) {
                            return color(d.name);
                          })
                          .attr("class", "legend-box");

    trends.append("text").attr("x", width + (margin.right/20) + 15) 
                         .attr("y", function (d, i) { return (legendSpace)+i*(legendSpace); })  // (return (11.25/2 =) 5.625) + i * (5.625)
                         .text(function(d) { return d.name; });

    var yAxisGrid = yAxis.ticks(( yMax < 5)? yMax : 5)
                         .tickSize(width, 0)
                         .tickFormat("")
                         .orient("right");

    var xAxisGrid = xAxis.ticks(5)
                         .tickSize(-height, 0)
                         .tickFormat("")
                         .orient("top");

    svg.append("g").attr("class", "grid").call(yAxisGrid);

    svg.append("g").attr("class", "grid").call(xAxisGrid);
}
