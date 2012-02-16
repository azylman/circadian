package com.zylman.alex;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.scribe.model.Token;

import com.zylman.alex.feed.source.FeedException;
import com.zylman.alex.feed.source.FeedSource;

public class User {
	private String name;
	private String email;
	private String linkedInToken;
	private String linkedInSecret;
	private List<FeedSource> sources = new ArrayList<FeedSource>();
	private Map<String, String> links = new LinkedHashMap<String, String>();
	List<Project> projects = new ArrayList<Project>();
	
	public class Project {
		private String name;
		private String description;
		private String url;
		
		public Project(String name, String description, String url) {
			this.name = name;
			this.description = description;
			this.url = url;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}
		
		public String getUrl() {
			return url;
		}
	}
	
	public User(String name, String email, String linkedInToken, String linkedInSecret) {
		this.name = name;
		this.email = email;
		this.linkedInToken = linkedInToken;
		this.linkedInSecret = linkedInSecret;
	}
	
	public String getName() {
		return name;
	}
	
	public Token getLinkedInToken() {
		return new Token(linkedInToken, linkedInSecret);
	}
	
	public String getEmail() {
		return email;
	}
	
	public void addSource(FeedSource source) {
		sources.add(source);
	}
	
	public void refreshSources() throws FeedException {
		for (FeedSource source : sources) {
			source.refresh(this);
		}
	}
	
	public void addLink(String name, String link) {
		links.put(name, link);
	}
	
	public Map<String, String> getLinks() {
		return links;
	}
	
	public void addProject(String name, String description, String url) {
		projects.add(new Project(name, description, url));
	}
	
	public List<Project> getProjects() {
		return projects;
	}
}
