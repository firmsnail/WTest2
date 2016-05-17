$(function() {
	
	//analyzeEmployeeByPeriod
	$.ajax({
		url: "/hr-manager/analyzeEmployeeByPeriod",
		dataType: 'json',
		success: function(data) {
			$('#analyzeEmployeeByPeriod').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: 'Distribution By Period'
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		        	type: 'pie',
		            name: 'Distribution By Period',
		            data: data
		        }]
		    });
		}
	});
	
	//analyzeEmployeeBySkill
	$.ajax({
		url: "/hr-manager/analyzeEmployeeBySkill",
		dataType: 'json',
		success: function(data) {
			$('#analyzeEmployeeBySkill').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: 'Distribution By Skill'
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		        	type: 'pie',
		            name: 'Distribution By Skill',
		            data: data
		        }]
		    });
		}
	});
	
	//analyzeEmployeeByGender
	$.ajax({
		url: "/hr-manager/analyzeEmployeeByGender",
		dataType: 'json',
		success: function(data) {
			$('#analyzeEmployeeByGender').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: 'Distribution By Gender'
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		        	type: 'pie',
		            name: 'Distribution By Gender',
		            data: data
		        }]
		    });
		}
	});
	
	//analyzeEmployeeByAge
	$.ajax({
		url: "/hr-manager/analyzeEmployeeByAge",
		dataType: 'json',
		success: function(data) {
			$('#analyzeEmployeeByAge').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: 'Distribution By Age'
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		        	type: 'pie',
		            name: 'Distribution By Age',
		            data: data
		        }]
		    });
		}
	});
	
	//analyzeEmployeeByDepartment
	$.ajax({
		url: "/hr-manager/analyzeEmployeeByDepartment",
		dataType: 'json',
		success: function(data) {
			$('#analyzeEmployeeByDepartment').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: 'Distribution By Department'
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		        	type: 'pie',
		            name: 'Distribution By Department',
		            data: data
		        }]
		    });
		}
	});
});