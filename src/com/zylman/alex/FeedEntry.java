package com.zylman.alex;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class FeedEntry {
	@PrimaryKey
	@Persistent
	String tweetId;
	
	@Persistent
	String user;
	
	@Persistent
	int source;
	
	@Persistent
	Text content;
	
	@Persistent
	Date time;
	
	FeedEntry(String user, String id, String content, Date time) {
		this.user = user;
		this.tweetId = id;
		this.content = new Text(content);
		this.time = time;
		this.source = 0;
	}
}
