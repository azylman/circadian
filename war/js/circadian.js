$(document).ready(function() {
	// Pull LinkedIn data from the cache for speed.
	$.ajax({
		url: "/linkedin",
		type: "get",
		dataType: "json",
		success: function(data) {
			updateProfile(data.person);
			
			// Refresh the cache for freshness.
			$.ajax({
				url: "/linkedin",
				type: "put",
				dataType: "json",
				success: function(data) {
					updateProfile(data.person);
				}
			});
		}
	});
	
	$.ajax({
		url: "/twitter",
		type: "get",
		dataType: "json",
		success: function(data) {
			updateFeed(data);
		}
	});
});

function updateFeed(feed) {
	var result = "";
	for (entry in feed) {
		result += addFeedEntry(feed[entry]);
	}
	$("#feed").html(result);
}

function addFeedEntry(entry) {
	var source = new Array();
	source["0"] = "Twitter";
	
	var result = "<div>";
	result += entry["content"];
	result += "<br />";
	result += entry["time"];
	result += " via ";
	result += source[entry["source"]];
	result += "</div>";
	
	return result;
}

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
	// Compensate for any issues with browser encoding etc. by creating an actual element
	// e.g. AT&T becomes AT&amp;T
	// If we didn't do this those would not compare properly and it would falsely update
	var element = $(document.createElement("div"));
	element.html(content);
	
	if (location.html() != element.html()) {
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
	
	result += "<div>";
	result += getDate(position["start-date"]);
	result += " - ";
	if (position["is-current"] == "true") {
		result += "present";
	} else {
		result += getDate(position["end-date"]);
	}
	result += "</div>";
	
	result += "<div>";
	result += position["title"];
	result += " @ ";
	result += position["company"]["name"];
	result += "</div>";
	
	result += "<div>";
	result += position["summary"];
	result += "</div>";
	
	result += "</div>";
	return result;
}

function getDate(date) {
	var result = "";
	if (date == null) return result;
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
	
	result += "<div>";
	result += getDate(education["start-date"]);
	result += " - ";
	result += getDate(education["end-date"]);
	result += "</div>";
	
	result += "<div>";
	result += education["school-name"];
	result += "</div>";
	
	result += "<div>";
	result += education["degree"];
	result += " in ";
	result += education["field-of-study"];
	result += "</div>";
	
	result += "<div>";
	result += "Activities: ";
	result += education["activities"];
	result += "</div>";
	
	result += "</div>";
	return result;
}