package com.zylman.alex;

import java.util.Date;

public class FeedEntry {
	int source;
	String content;
	Date time;
	
	FeedEntry(String content, Date time) {
		this.content = content;
		this.time = time;
		this.source = 0;
	}
}
