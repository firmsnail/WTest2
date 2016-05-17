$(function() {
	
	//analyzeEmployeeByPeriod
	$.ajax({
		url: "/hr-manager/analyzePayrollByPeriod",
		dataType: 'json',
		success: function(data) {
			$('#analyzePayrollByPeriod').highcharts({
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
		url: "/hr-manager/analyzePayrollBySkill",
		dataType: 'json',
		success: function(data) {
			$('#analyzePayrollBySkill').highcharts({
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
		url: "/hr-manager/analyzePayrollByGender",
		dataType: 'json',
		success: function(data) {
			$('#analyzePayrollByGender').highcharts({
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
		url: "/hr-manager/analyzePayrollByAge",
		dataType: 'json',
		success: function(data) {
			$('#analyzePayrollByAge').highcharts({
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
		url: "/hr-manager/analyzePayrollByDepartment",
		dataType: 'json',
		success: function(data) {
			$('#analyzePayrollByDepartment').highcharts({
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