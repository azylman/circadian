package com.zylman.alex;

import java.util.Date;

public class FeedEntry {
	String id;
	int source;
	String content;
	Date time;
	
	FeedEntry(String id, String content, Date time) {
		this.id = id;
		this.content = content;
		this.time = time;
		this.source = 0;
	}
}
