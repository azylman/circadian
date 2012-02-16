package com.zylman.alex.feed.source;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.zylman.alex.PMF;
import com.zylman.alex.User;
import com.zylman.alex.blogger.BloggerClient;
import com.zylman.alex.blogger.BloggerPost;
import com.zylman.alex.feed.Feed;
import com.zylman.alex.feed.FeedEntry;
import com.zylman.alex.feed.FeedHelper;

public class BloggerSource extends FeedSource {
	private String blogId;

	public BloggerSource(String blogId, String blogAddress) {
		super("Blogger", blogAddress);
		this.blogId = blogId;
	}

	@Override public void refresh(User user) throws FeedException {
		BloggerClient blogger = new BloggerClient();
		
		FeedEntry mostRecentBlogPost = findMostRecentBlogPost(user);

		List<BloggerPost> blogPosts;
		if (mostRecentBlogPost == null) {
			blogPosts = blogger.getPosts(blogId);
		} else {
			blogPosts = blogger.getPosts(blogId, mostRecentBlogPost);
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		for (BloggerPost post : blogPosts) {
			FeedEntry entry = new FeedEntry(user, post);
			pm.makePersistent(entry);
		}
		pm.close();
	}

	public String tempOutput() {
		BloggerClient blogger = new BloggerClient();

		return blogger.getPosts(blogId).toString();
	}

	private FeedEntry findMostRecentBlogPost(User user) {
		int page = 1;
		
		Feed feed;
		do {
			feed = FeedHelper.get(user, page);
			page++;
		} while (!feed.isEmpty() && !feedContainsBloggerPost(feed));
		
		return getMostRecentBlogPostFromFeed(feed);
	}
	
	private FeedEntry getMostRecentBlogPostFromFeed(Feed feed) {
		if (feed.isEmpty()) return null;
		
		for (FeedEntry entry : feed.getEntries()) {
			if (feedEntryIsBloggerEntry(entry)) {
				return entry;
			}
		}
		
		return null;
	}
	
	private boolean feedContainsBloggerPost(Feed feed) {
		for (FeedEntry entry : feed.getEntries()) {
			if (feedEntryIsBloggerEntry(entry)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean feedEntryIsBloggerEntry(FeedEntry entry) {
		return entry.getId().contains("blogger");
	}
}
