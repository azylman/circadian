$(document).ready(function() {
	$.ajax({
		url: "/linkedin",
		dataType: "json",
		success: function(data) {
			updateProfile(data.person);
		}
	});
});

// first-name, last-name, headline, location:(name), industry, summary
// specialties, honors, interests, positions, publications, patents
// languages, skills, certifications, educations, picture-url, public-profile-url

function updateProfile(me) {
	updateIfDifferent($("#name"), me["first-name"] + " " + me["last-name"]);
	updateIfDifferent($("#headline"), me["headline"]);
	updateIfDifferent($("#location"), me["location"]["name"]);
	updateIfDifferent($("#industry"), me["industry"]);
	updateIfDifferent($("#summary"), me["summary"]);
	updateIfDifferent($("#specialties"), me["specialties"]);
	updateIfDifferent($("#honors"), me["honors"]);
	updateIfDifferent($("#interests"), me["interests"]);
	//updateIfDifferent($("#positions"), me["positions"]);
	updateIfDifferent($("#publications"), me["publications"]);
	updateIfDifferent($("#patents"), me["patents"]);
	updateIfDifferent($("#languages"), me["languages"]);
	updateIfDifferent($("#skills"), compileSkills(me["skills"]["skill"]));
	updateIfDifferent($("#certifications"), me["certifications"]);
	//updateIfDifferent($("#educations"), me["educations"]);
	updateImageIfDifferent($("#profile-picture"), me["picture-url"]);
	$("#profile-link").attr('href', me["public-profile-url"]);
}

function updateIfDifferent(location, content) {
	if (location.html() != content) {
		updateContent(location, content);
	}
}

function updateContent(location, content) {
	location.fadeOut('fast', function() {
		location.html(content);
		location.fadeIn('fast', function() {});
	});
}

function updateImageIfDifferent(image, location) {
	if (image.attr('src') != location) {	
		image.fadeOut('fast', function() {
			image.attr('src', location);
			image.fadeIn('fast', function() {});
		});
	}
}

function compileSkills(skills) {
	var result = "";
	for (skill in skills) {
		result += skills[skill]["skill"]["name"] + ", ";
	}
	return result.substring(0, result.length - 2);
}