package com.zylman.alex.feed;

import java.net.URL;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import twitter4j.Status;
import twitter4j.URLEntity;

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
	private String source;
	
	@Persistent
	private Text title;
	
	@Persistent
	private Text content;
	
	@Persistent
	private Date time;
	
	@Persistent
	private String url;
	
	private FeedEntry(User user, String id, String title, String content, Date time, String url, String source) {
		this.user = user.getEmail();
		this.id = id;
		this.title = new Text(title);
		this.content = new Text(content);
		this.time = time;
		this.url = url;
		this.source = source;
	}
	
	public FeedEntry(User user, Status tweet) {
		this(
			user,
			"tweet-" + Long.toString(tweet.getId()),
			"",
			getTwitterText(tweet),
			tweet.getCreatedAt(),
			"https://twitter.com/#!/" + tweet.getUser().getScreenName() + "/status/" + tweet.getId(),
			"Twitter");
	}
	
	public FeedEntry(User user, BloggerPost post) {
		this(
			user,
			getBloggerId(post),
			post.getTitle(),
			post.getText(),
			post.getDate(),
			post.getUrl(),
			"Blogger");
	}
	
	public String getId() {
		return id;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getSource() {
		return source;
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
	
	public String getUrl() {
		return url;
	}
	
	public static String getBloggerId(BloggerPost post) {
		return "blogger-" + post.getId();
	}
	
	private static String getTwitterText(Status tweet) {
		String text = tweet.getText();
		for (URLEntity urlEntity : tweet.getURLEntities()) {
			String textToReplace = urlEntity.getURL().toString();
			String linkToSubstitute = createLink(urlEntity.getDisplayURL(), urlEntity.getExpandedURL());
			text = text.replace(textToReplace, linkToSubstitute);
		}
		return text;
	}
	
	private static String createLink(String text, URL url) {
		return "<a href=\"" + url.toString() + "\">" + text + "</a>";
	}
}
