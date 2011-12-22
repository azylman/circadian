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
				url: "/linkedin/recache",
				type: "get",
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

function replaceURLWithHTMLLinks(text) {
    var exp = /(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig;
    return text.replace(exp,"<a href='$1'>$1</a>"); 
}

function updateFeed(feed) {
	var result = "";
	for (entry in feed) {
		result += addFeedEntry(feed[entry]);
	}
	
	$("#feed").html(result);
	
	twttr.anywhere(function (T) {
		T.hovercards();
	});
	
	twttr.anywhere(function (T) {
		T.linkifyUsers();
	});
}

function addFeedEntry(entry) {
	var source = new Array();
	source["0"] = "Twitter";
	
	var result = "<div class='feed-entry'>";
	
	result += "<div class='feed-entry-content'>";
	result += replaceURLWithHTMLLinks(entry["content"]);
	result += "</div>";
	result += "<span class='feed-entry-time'>";
	result += entry["time"];
	result += "</span>";
	result += "<span class='feed-entry-source'>";
	result += source[entry["source"]];
	result += "</span>";
	
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
	var result = "<div class='position-entry'>";
	
	result += "<div class='position-name'>";
	result += position["title"];
	result += " @ ";
	result += position["company"]["name"];
	result += "</div>";

	result += "<div class='position-date'>";
	result += getDate(position["start-date"]);
	result += " - ";
	if (position["is-current"] == "true") {
		result += "present";
	} else {
		result += getDate(position["end-date"]);
	}
	result += "</div>";
	
	result += "<div class='position-summary'>";
	result += position["summary"];
	result += "</div>";
	
	result += "</div>";
	return result;
}

function getDate(time) {
	var result = "";
	if (time == null) return result;
	if (time["day"] != null) {
		result += time["day"] += "-";
	}
	if (time["month"] != null) {
		result += time["month"] += "-";
	}
	if (time["year"] != null) {
		result += time["year"];
	}
	return date("M Y", result);
}

function compileEducation(education) {
	return createEducation(education);
}

function createEducation(education) {
	var result = "<div class='school-entry'>";

	result += "<div class='school-name'>";
	result += education["school-name"];
	result += "</div>";
	
	result += "<div class='school-degree'>";
	result += education["degree"];
	result += " in ";
	result += education["field-of-study"];
	result += "</div>";
	
	result += "<div class='school-date'>";
	result += getDate(education["start-date"]);
	result += " - ";
	result += getDate(education["end-date"]);
	result += "</div>";
		
	result += "<div class='school-activities'>";
	result += education["activities"];
	result += "</div>";
	
	result += "</div>";
	return result;
}