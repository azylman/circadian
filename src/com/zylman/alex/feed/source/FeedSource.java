package com.zylman.alex.feed.source;

import com.zylman.alex.User;

public abstract class FeedSource {
	private String name;
	private String link;
	private int sourceNum;
	
	public FeedSource(String name, String link) {
		this.name = name;
		this.link = link;
	}
	
	public abstract void refresh(User user) throws FeedException;
	
	public String getName() {
		return name;
	}
	
	public String getLink() {
		return link;
	}
	
	public int getSourceNum() {
		return sourceNum;
	}
	
	public void setSourceNum(int sourceNum) {
		this.sourceNum = sourceNum;
	}
}
