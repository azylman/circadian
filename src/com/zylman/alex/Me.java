package com.zylman.alex;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsr107cache.CacheException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.zylman.alex.User.Project;
import com.zylman.alex.feed.Feed;
import com.zylman.alex.feed.FeedEntry;
import com.zylman.alex.feed.FeedHelper;
import com.zylman.alex.profile.LinkedInHelper;
import com.zylman.alex.profile.LinkedInProfile;

import freemarker.template.SimpleSequence;

public class Me extends ServerResource {
	@Get public Representation toText() throws IOException, JSONException, CacheException {
		User user = HiddenData.getAdmin();
		
		String pageNum = getAttribute("pageNum");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sources", user.getSourcesString());
		map.put("pageNum", pageNum == null || pageNum.isEmpty() ? "1" : pageNum);
		map.put("name", user.getName());
		map.put("links", parseLinks(user.getLinks()));
		map.put("projects", parseProjects(user.getProjects()));

		return populateTemplate(map, "user.html");
	}
	
	private SimpleSequence parseLinks(Map<String, String> links) {
		SimpleSequence result = new SimpleSequence();
		
		for (Map.Entry<String, String> link : links.entrySet()) {
			Map<String, String> linkAttributes = new HashMap<String, String>();
			linkAttributes.put("name", link.getKey());
			linkAttributes.put("link", link.getValue());
			result.add(linkAttributes);
		}
		
		return result;
	}
	
	private SimpleSequence parseProjects(List<Project> projects) {
		SimpleSequence result = new SimpleSequence();
		
		for (Project project : projects) {
			Map<String, String> projectAttributes = new HashMap<String, String>();
			projectAttributes.put("name", project.getName());
			projectAttributes.put("description", project.getDescription());
			projectAttributes.put("url", project.getUrl());
			result.add(projectAttributes);
		}
		
		return result;
	}

	private String replaceURLWithHTMLLinks(String text) {
		String pattern = "(?i)(\\b(https?|ftp|file):\\/\\/[-A-Z0-9+&@#\\/%?=~_|!:,.;]*[-A-Z0-9+&@#\\/%=~_|])";
		return text.replaceAll(pattern, "<a href=\"$1\">$1</a>");
	}
	
	/**
	 * Parses a user's feed into a format readable by FreeMarker. Used for loading data server-side.
	 * Currently unused - just using AJAX loading, client-side.
	 */
	@SuppressWarnings("unused")
	private SimpleSequence parseFeed(User user) {
		Feed feed = FeedHelper.get(user, 1);
		SimpleSequence parsedFeed = new SimpleSequence();

		for (FeedEntry entry : feed.getEntries()) {
			Map<String, String> parsedEntry = new HashMap<String, String>();

			String content = entry.getContent();
			content = replaceURLWithHTMLLinks(content);
			parsedEntry.put("content", content);
			parsedEntry.put("time", entry.getTime().toString());
			parsedEntry.put("source", Integer.toString(entry.getSource()));

			parsedFeed.add(parsedEntry);
		}
		
		return parsedFeed;
	}
	
	/**
	 * Parses a user's profile into a format readable by FreeMarker. Used for loading data server-side.
	 * Currently unused - just using AJAX loading, client-side.
	 * 
	 * NOTE: This function is currently INCOMPLETE and will not work. Halfway through implementation I
	 * decided that it wasn't worth it to load data server-side but didn't want to get rid of it in case
	 * I changed my mind. - az, 12/25/2011
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> parseProfile(User user) throws JSONException {
		Map<String, Object> parsedProfile = new HashMap<String, Object>();
		try {
			LinkedInProfile linkedIn = LinkedInHelper.get(user);
			JSONObject linkedInProfile = new JSONObject(linkedIn.getProfile());
			linkedInProfile = linkedInProfile.getJSONObject("person");
			
			// Why does org.json not just return null when the key doesn't exist?
			// There's NO reason to throw an exception that's going to be ignored nine times out of ten.
			try {
				String name = linkedInProfile.getString("first-name") + " " + linkedInProfile.getString("last-name");
				parsedProfile.put("name", name);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String headline = linkedInProfile.getString("headline");
				parsedProfile.put("headline", headline);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String location = linkedInProfile.getJSONObject("location").getString("name");
				parsedProfile.put("location", location);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String industry = linkedInProfile.getString("industry");
				parsedProfile.put("industry", industry);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String summary = linkedInProfile.getString("summary");
				parsedProfile.put("summary", summary);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String specialties = linkedInProfile.getString("specialties");
				parsedProfile.put("specialties", specialties);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String honors = linkedInProfile.getString("honors");
				parsedProfile.put("honors", honors);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String interests = linkedInProfile.getString("interests");
				parsedProfile.put("interests", interests);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				JSONArray positions = linkedInProfile.getJSONObject("positions").getJSONArray("position");
				SimpleSequence positionSequence = new SimpleSequence();
				for (int i = 0; i < positions.length(); ++i) {
					JSONObject position = positions.getJSONObject(i);
					Map<String, String> positionMap = new HashMap<String, String>();
					positionMap.put("title", position.getString("title"));
				}
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String publications = linkedInProfile.getString("publications");
				parsedProfile.put("publications", publications);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String patents = linkedInProfile.getString("patents");
				parsedProfile.put("patents", patents);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String languages = linkedInProfile.getString("languages");
				parsedProfile.put("languages", languages);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				JSONArray skills = linkedInProfile.getJSONObject("skills").getJSONArray("skill");
				SimpleSequence skillsSequence = new SimpleSequence();
				for (int i = 0; i < skills.length(); ++i) {
					JSONObject position = skills.getJSONObject(i);
				}
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String certifications = linkedInProfile.getString("certifications");
				parsedProfile.put("certifications", certifications);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				JSONObject education = linkedInProfile.getJSONObject("educations").getJSONObject("education");
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String pictureUrl = linkedInProfile.getString("picture-url");
				parsedProfile.put("pictureUrl", pictureUrl);
			} catch (JSONException e) {
				// Not sent in our response
			}
			
			try {
				String publicProfileUrl = linkedInProfile.getString("public-profile-url");
				parsedProfile.put("publicProfileUrl", publicProfileUrl);
			} catch (JSONException e) {
				// Not sent in our response
			}
		} catch (CacheException e) {
			// Hopefully never happens...
		}
		
		return parsedProfile;
	}
}
