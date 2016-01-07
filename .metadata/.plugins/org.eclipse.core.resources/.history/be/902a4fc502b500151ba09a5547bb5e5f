var margin = {top: 20, right: 80, bottom: 70, left: 50},
    width = 960 - margin.left - margin.right,
    height = 540 - margin.top - margin.bottom;

var parseDate = d3.time.format("%H:%M:%S").parse;

var x = d3.time.scale()
    .range([0, width]);

var y = d3.scale.linear()
    .range([height, 0]);

var color = d3.scale.category10();

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom")
    .tickFormat(d3.time.format("%X"));

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left");

var line = d3.svg.line()
    //.interpolate("basis")
    .x(function(d) { return x(d.time); })
    .y(function(d) { return y(d.throughput); });

var svg = d3.select("body").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

function make_x_axis() {    
    return d3.svg.axis()
        .scale(x)
        .orient("bottom")
        .ticks(24)
}

function make_y_axis() {    
    return d3.svg.axis()
        .scale(y)
        .orient("left")
        .ticks(24)
}

function render(){
    d3.tsv("res/data/test.tsv", function(error, data) {
        if (error) throw error;

        color.domain(d3.keys(data[0]).filter(function(key) { return key !== "time"; }));

        data.forEach(function(d) {
            d.time = parseDate(d.time);
        });

        var trends = color.domain().map(function(name) {
            return {
                name: name,
                values: data.map(function(d) {
                    return {time: d.time, throughput: +d[name]};
                })
            };
        });

        x.domain(d3.extent(data, function(d) { return d.time; }));

        y.domain([
            0/*d3.min(trends, function(c) { return d3.min(c.values, function(v) { return v.throughput; }); })*/,
            d3.max(trends, function(c) { return d3.max(c.values, function(v) { return v.throughput; }); }) + 5
        ]);

        svg.selectAll(".y.axis").remove();
        svg.selectAll(".x.axis").remove();
        svg.selectAll(".grid").remove();
        svg.selectAll(".trend").remove();
        svg.selectAll(".label").remove();

        svg.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis);

        svg.append("g")
            .attr("class", "y axis")
            .call(yAxis)
            .append("text")
            .attr("transform", "rotate(-90)")
            .attr("y", 6)
            .attr("dy", ".71em")
            .style("text-anchor", "end")
            .text("Throughput");

        svg.append("g")     
            .attr("class", "grid")
            .attr("transform", "translate(0," + height + ")")
            .call(make_x_axis()
                .tickSize(-height, 0, 0)
                .tickFormat("")
            );

        svg.append("g")     
            .attr("class", "grid")
            .call(make_y_axis()
                .tickSize(-width, 0, 0)
                .tickFormat("")
            );

        var trend = svg.selectAll(".trend")
            .data(trends)
            .enter().append("g")
            .attr("class", "trend");

        trend.append("path")
            .attr("class", "line")
            .attr("d", function(d) { return line(d.values); })
            .style("stroke", function(d) { return color(d.name); });

        var legendSpace = width/trends.length;
        //var i = 0
        for(i = 0 ; i < trends.length; i++){

            trend.append("text")
                .attr("class", "label")
                .attr("x", (legendSpace/2)+(i)*legendSpace)
                .attr("y", height + (margin.bottom/2)+ 5) 
                .attr("dy", ".35em")
                .text(function(d) { return trends[i].name.toUpperCase(); })
                .style("fill", function(d) { return color(trends[i].name); });
        }
    });
}

render();
setInterval(function() { render(); }, 1000);