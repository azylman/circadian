package com.zylman.alex;

import java.util.HashMap;
import java.util.Map;

import org.scribe.model.Token;

import com.zylman.alex.feed.source.FeedException;
import com.zylman.alex.feed.source.FeedSource;

public class User {
	private String name;
	private String email;
	private String linkedInToken;
	private String linkedInSecret;
	private Map<Integer, FeedSource> sources = new HashMap<Integer, FeedSource>();
	
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
		return new Token(linkedInSecret, linkedInToken);
	}
	
	public String getEmail() {
		return email;
	}
	
	public void addSource(int id, FeedSource source) {
		sources.put(id, source);
	}
	
	public String getSourcesString() {
		StringBuilder sourceNames = new StringBuilder();
		StringBuilder sourceLinks = new StringBuilder();
		
		sourceNames.append("var sourceName = new Array();\n");
		sourceLinks.append("var sourceLinks = new Array();\n");
		for (Map.Entry<Integer, FeedSource> source : sources.entrySet()) {
			sourceNames.append("sourceName[" + source.getKey() + "] = \"" + source.getValue().getName() + "\";\n");
			sourceLinks.append("sourceLinks[" + source.getKey() + "] = \"" + source.getValue().getLink() + "\";\n");
		}
		
		return sourceNames.toString() + "\n" + sourceLinks.toString();
	}
	
	public void refreshSources() throws FeedException {
		for (FeedSource source : sources.values()) {
			source.refresh(this);
		}
	}
}
