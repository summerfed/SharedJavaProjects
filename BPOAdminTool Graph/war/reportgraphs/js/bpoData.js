	function getUrlParam(nodeIdParm){
		return	unescape ( window . location . search . replace ( new  RegExp ( "^(?:.*[&\\?]"  +  escape ( nodeIdParm ) . replace ( /[\.\+\*]/g ,  "\\$&" )  +  "(?:\\=([^&]*))?)?.*$" ,  "i" ) ,  "$1" ) ) ; 
	}

	var liquidgaugeconfig = liquidFillGaugeDefaultSettings();
    liquidgaugeconfig.circleThickness = 0.05;
    liquidgaugeconfig.circleColor = "#7777FF";
    liquidgaugeconfig.textColor = "#4444FF";
    liquidgaugeconfig.waveTextColor = "#AAAAFF";
    liquidgaugeconfig.waveColor = "#DDDDFF";
    liquidgaugeconfig.textVertPosition = 0.8;
    liquidgaugeconfig.waveAnimateTime = 1000;
    liquidgaugeconfig.waveHeight = 0.05;
    liquidgaugeconfig.waveAnimate = true;
    liquidgaugeconfig.waveRise = false;
    liquidgaugeconfig.waveHeightScaling = false;
    liquidgaugeconfig.waveOffset = 0.25;
    liquidgaugeconfig.textSize = 0.75;
    liquidgaugeconfig.waveCount = 3;	
	
	//console.log($.ajax("/bporeports?nodeId=test"));
	var config = defaultMultiSeriesLineGraphConfig;
	config.container = "#waiting-vs-inprocess";
	config.outerWidth = $(config.container).width();
	$.ajax("/bporeports?nodeId=" + getUrlParam("nodeId")).done(function(data){
		try{
			// console.log("Data" + data);
			config.jsonStr = $.parseJSON(data);
			// console.log("VALID");
			// console.log(config.jsonStr);
			generateMultiSeriesLineGraph(config);
		} catch (err) {
			console.log("invalid json" + err.message);
		}
	});
	
	var prcentErr;
	var prcentOut;
	var aveDurData = 0;
	
	var errorpercentage;
	$.ajax("/reatltimerpt?nodeId=" + getUrlParam("nodeId")).done(function(errData){
		try{
			var jsonStr = $.parseJSON(errData);
			prcentErr = jsonStr[jsonStr.length-1].percentError;
			prcentOut = jsonStr[jsonStr.length-1].percentOutput;
			
			console.log("ERROR: " + prcentErr);
			console.log("OUTPUT: " + prcentOut);
			if( (isNaN(parseFloat(prcentErr)) || isNaN(parseFloat(prcentOut))) ||
				(parseFloat(prcentErr) <= 0 && parseFloat(prcentOut) <= 0)){
				$("#percentage-error").hide();
				$("#errorpiedefault").show();
			} else {
				errorpercentage = c3.generate({
					bindto: '#percentage-error',
					data: {
						columns: [
							['error', parseFloat(prcentErr)],
							['passed', parseFloat(prcentOut)],
						],
					type : 'pie'
					}
				});
				$("#percentage-error").show();
				$("#errorpiedefault").hide();
			}
		}
		catch(err){
			console.log("invalid json:" + err.message);
		}
	});
	
    var maxdur = 60*2;
    var thresholds = [maxdur/4, maxdur/2, maxdur/4 + maxdur/2, maxdur];
	
	var avedur = c3.generate({
		bindto: "#ave-duration",
	    data: {
	        columns: [
	            ['data', 0]
	        ],
	        type: 'gauge'
	    },
	    gauge: {
	        label: {
	            format: function(value, ratio) {
	                return value;
	            },
	        },
			max: maxdur,
			units: ' minutes',
	    },
	    color: {
					// the three color levels for the percentage values.
	        pattern: ['#60B044', '#F6C600', '#F97600', '#FF0000'],
	        threshold: {
	            unit: ' minutes',
	            values: [thresholds[0], thresholds[1], thresholds[2], thresholds[3]]
	        }
	    },
	    size: {
	        height: 150
	    }
	});
	
	
	//LiquidFillGuage
	//enclose the code below in an ajax done snippet
    var gauge;
	$.ajax("/nodeprogress?nodeId=" + getUrlParam("nodeId")).done(function(liquid){	
		try{
			jsonStr = $.parseJSON(liquid);
			// console.log("VALID");
			//console.log("Guage: "+jsonStr);
			var progress = jsonStr[0].nodeProgress;
			gauge = loadLiquidFillGauge("node-progress", 0, liquidgaugeconfig);
			console.log("Liquid Guage default value is 0%");
		} catch (err) {
			console.log("invalid json" + err.message);
		}
	});
	
	setInterval(function() {
		config.outerWidth = $(config.container).width();
		$.ajax("/bporeports?nodeId=" + getUrlParam("nodeId")).done(function(data){
			try{
				config.jsonStr = $.parseJSON(data);
				
				updateMultiSeriesLineGraph(config);
			} catch (err) {
				console.log("invalid json" + err.message);
			}
		});
		
		$.ajax("/reatltimerpt?nodeId=" + getUrlParam("nodeId")).done(function(errData){
			try{
				var jsonStr = $.parseJSON(errData);
				prcentErr = jsonStr[jsonStr.length-1].percentError;
				prcentOut = jsonStr[jsonStr.length-1].percentOutput;
				
				console.log("ERROR: " + prcentErr);
				console.log("OUTPUT: " + prcentOut);
				if( (isNaN(parseFloat(prcentErr)) || isNaN(parseFloat(prcentOut))) ||
					(parseFloat(prcentErr) <= 0 && parseFloat(prcentOut) <= 0)){
					$("#percentage-error").hide();
					$("#errorpiedefault").show();
				} else {
					errorpercentage.load({
						columns:[
							['error', parseFloat(prcentErr)],
							['passed', parseFloat(prcentOut)],
						]
					});
					$("#percentage-error").show();
					$("#errorpiedefault").hide();
				}
				// console.log("OUTPUT:" + prcentOut);
				// console.log("ERROR:" + prcentErr);
			}
			catch(err){
				console.log("invalid json:" + err.message);
			}
		});
		
		$.ajax("/aveduration?nodeId=" + getUrlParam("nodeId")).done(function(durData){
			try{
				// console.log(durData);
				var jsonData = $.parseJSON(durData);
				aveDurData = jsonData[jsonData.length-1].aveProcessDuration;
				avedur.load({
					columns: [
						['data', parseInt((aveDurData / (60 * 1000)))]
					]
				});
			}
			catch(err){
				console.log("invalid json:" + err.message);
			}
		});
		
	$.ajax("/nodeprogress?nodeId=" + getUrlParam("nodeId")).done(function(liquid){
		try{
			jsonStr = $.parseJSON(liquid);
			// console.log("VALID");
			//console.log("Guage: "+jsonStr);
			var progress = jsonStr[jsonStr.length-1].nodeProgress;
			// console.log("Liquid Guage value: " + progress);
			//gauge = loadLiquidFillGauge("node-progress", parseFloat(progress), liquidgaugeconfig);
			gauge.update(parseFloat(progress));
		} catch (err) {
			console.log("invalid json" + err.message);
		}
		
	});

	}, 1000);