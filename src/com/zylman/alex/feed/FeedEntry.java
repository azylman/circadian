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
	private Text content;
	
	@Persistent
	private Date time;
	
	public FeedEntry(User user, String id, String content, Date time) {
		this.user = user.getEmail();
		this.id = id;
		this.content = new Text(content);
		this.time = time;
	}
	
	public FeedEntry(User user, Status tweet) {
		this(user, "tweet-" + Long.toString(tweet.getId()), tweet.getText(), tweet.getCreatedAt());
	}
	
	public FeedEntry(User user, BloggerPost post) {
		this(user, getBloggerId(post), combinePostTitleAndText(post), post.getDate());
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
	
	public String getContent() {
		return content.getValue();
	}
	
	public Date getTime() {
		return time;
	}
	
	private static String combinePostTitleAndText(BloggerPost post) {
		return "<div class=\"blogger-title\">" + post.getTitle() + "</div><br/>" + post.getText();
	}
	
	public static String getBloggerId(BloggerPost post) {
		return "blogger-" + post.getId();
	}
}
