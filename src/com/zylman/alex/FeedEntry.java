package com.zylman.alex;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class FeedEntry {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long key;
	
	@Persistent
	String user;
	
	@Persistent
	String id;
	
	@Persistent
	int source;
	
	@Persistent
	Text content;
	
	@Persistent
	Date time;
	
	FeedEntry(String id, String content, Date time) {
		this.id = id;
		this.content = new Text(content);
		this.time = time;
		this.source = 0;
	}
}
