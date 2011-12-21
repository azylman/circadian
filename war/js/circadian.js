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
	updateIfDifferent($("#positions"), compilePositions(me["positions"]["position"]));
	updateIfDifferent($("#publications"), me["publications"]);
	updateIfDifferent($("#patents"), me["patents"]);
	updateIfDifferent($("#languages"), me["languages"]);
	updateIfDifferent($("#skills"), compileSkills(me["skills"]["skill"]));
	updateIfDifferent($("#certifications"), me["certifications"]);
	updateIfDifferent($("#educations"), compileEducation(me["educations"]["education"]));
	updateImageIfDifferent($("#profile-picture"), me["picture-url"]);
	$("#profile-link").attr('href', me["public-profile-url"]);
}

function updateIfDifferent(location, content) {
	if (location.html() != content) {
		updateContent(location, content);
	} else {
		hideIfEmpty(location);
	}
}

function updateContent(location, content) {
	location.fadeOut('fast', function() {
		location.html(content);
		location.fadeIn('fast', function() {
			hideIfEmpty(location);
		});
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

function hideIfEmpty(location) {
	if (location.html() == "") {
		location.parent().hide();
	} else {
		location.parent().show();
	}
}

function compileSkills(skills) {
	var result = "";
	for (skill in skills) {
		result += skills[skill]["skill"]["name"] + ", ";
	}
	return result.substring(0, result.length - 2);
}

function compilePositions(positions) {
	var result = "";
	for (position in positions) {
		result += createPosition(positions[position]);
	}
	return result;
}

function createPosition(position) {
	var result = "<div>";
	result += getDate(position["start-date"]);
	result += " - ";
	if (position["is-current"] == true) {
		result += "present";
	} else {
		result += getDate(position["end-date"]);
	}
	result += "<br />";
	result += position["title"];
	result += " @ ";
	result += position["company"]["name"];
	result += "<br />";
	result += position["summary"];
	result += "</div>";
	return result;
}

function getDate(date) {
	var result = "";
	if (date["month"] != null) {
		result += date["month"] += "-";
	}
	if (date["day"] != null) {
		result += date["day"] += "-";
	}
	if (date["year"] != null) {
		result += date["year"];
	}
	return result;
}

function compileEducation(education) {
	return createEducation(education);
}

function createEducation(education) {
	var result = "<div>";
	result += getDate(education["start-date"]);
	result += " - ";
	result += getDate(education["end-date"]);
	result += "<br />";
	result += education["school-name"];
	result += "<br />";
	result += education["degree"];
	result += " in ";
	result += education["field-of-study"];
	result += "<br />";
	result += "Activities: ";
	result += education["activities"];
	return result;
}