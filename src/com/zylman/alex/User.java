package com.zylman.alex;

import java.util.HashMap;
import java.util.Map;

import org.scribe.model.Token;

public class User {
	public String email;
	public String twitterID;
	public String linkedInToken;
	public String linkedInSecret;
	private Map<Integer, Source> sources = new HashMap<Integer, Source>();
	
	public Token getLinkedInToken() {
		return new Token(linkedInSecret, linkedInToken);
	}
	
	public String getTwitterID() {
		return twitterID;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void addSource(int id, String name, String link) {
		sources.put(id, new Source(name, link));
	}
	
	public String getSourcesString() {
		StringBuilder sourceNames = new StringBuilder();
		StringBuilder sourceLinks = new StringBuilder();
		
		sourceNames.append("var sourceName = new Array();\n");
		sourceLinks.append("var sourceLinks = new Array();\n");
		for (Map.Entry<Integer, Source> source : sources.entrySet()) {
			sourceNames.append("sourceName[" + source.getKey() + "] = \"" + source.getValue().getName() + "\";\n");
			sourceLinks.append("sourceLinks[" + source.getKey() + "] = \"" + source.getValue().getLink() + "\";\n");
		}
		
		return sourceNames.toString() + "\n" + sourceLinks.toString();
	}
	
	public class Source {
		private String name;
		private String link;
		
		public Source(String name, String link) {
			this.name = name;
			this.link = link;
		}
		
		public String getName() {
			return name;
		}
		
		public String getLink() {
			return link;
		}
	}
}
