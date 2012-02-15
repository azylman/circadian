package com.zylman.alex.feed;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import twitter4j.Status;

import com.google.appengine.api.datastore.Text;
import com.zylman.alex.User;
import com.zylman.alex.blogger.BloggerPost;

@PersistenceCapable
public class FeedEntry {
	@PrimaryKey
	@Persistent
	private String id;
	
	@Persistent
	private String user;
	
	@Persistent
	private int source;
	
	@Persistent
	private Text title;
	
	@Persistent
	private Text content;
	
	@Persistent
	private Date time;
	
	private FeedEntry(User user, String id, String title, String content, Date time) {
		this.user = user.getEmail();
		this.id = id;
		this.title = new Text(title);
		this.content = new Text(content);
		this.time = time;
	}
	
	public FeedEntry(User user, Status tweet) {
		this(user, "tweet-" + Long.toString(tweet.getId()), "", tweet.getText(), tweet.getCreatedAt());
	}
	
	public FeedEntry(User user, BloggerPost post) {
		this(user, getBloggerId(post), post.getTitle(), post.getText(), post.getDate());
	}
	
	public String getId() {
		return id;
	}
	
	public String getUser() {
		return user;
	}
	
	public int getSource() {
		return source;
	}
	
	public void setSource(int source) {
		this.source = source;
	}
	
	public String getTitle() {
		return title.getValue();
	}
	
	public String getContent() {
		return content.getValue();
	}
	
	public Date getTime() {
		return time;
	}
	
	public static String getBloggerId(BloggerPost post) {
		return "blogger-" + post.getId();
	}
}
