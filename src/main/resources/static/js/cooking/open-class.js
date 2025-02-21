/**
 * 
 */

$(document).ready(function () {
    $("#datepicker").datepicker({
      format: "yyyy-mm-dd",
      autoclose: true,
      todayHighlight: true,
    });
	
	
	$('#timepicker').timepicker({
        timeFormat: 'HH:mm',
        interval: 30,
        minTime: '08',
        maxTime: '22:00',
        defaultTime: '11',
        startTime: '08:00',
        dynamic: false,
        dropdown: true,
        scrollbar: true
    });
});