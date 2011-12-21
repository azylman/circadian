$(document).ready(function() {
	$.ajax({
		url: "/linkedin",
		dataType: "html",
		success: function(data) {
			$("#results").html(data);
		}
	});
});