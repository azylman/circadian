package com.zylman.alex.feed;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import twitter4j.Status;

import com.google.appengine.api.datastore.Text;
import com.zylman.alex.User;

@PersistenceCapable
public class FeedEntry {
	@PrimaryKey
	@Persistent
	private String tweetId;
	
	@Persistent
	private String user;
	
	@Persistent
	private int source;
	
	@Persistent
	private Text content;
	
	@Persistent
	private Date time;
	
	public FeedEntry(User user, String id, String content, Date time) {
		this.user = user.getEmail();
		this.tweetId = id;
		this.content = new Text(content);
		this.time = time;
		this.source = 0;
	}
	
	public FeedEntry(User user, Status tweet) {
		this(user, "tweet-" + Long.toString(tweet.getId()), tweet.getText(), tweet.getCreatedAt());
	}
	
	public String getId() {
		return tweetId;
	}
	
	public String getUser() {
		return user;
	}
	
	public int getSource() {
		return source;
	}
	
	public String getContent() {
		return content.getValue();
	}
	
	public Date getTime() {
		return time;
	}
}
