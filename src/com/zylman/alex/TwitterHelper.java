package com.zylman.alex;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterHelper {
	private static Cache cache = null;
	private static final int MAX_PAGES = Integer.MAX_VALUE;
	
	private static void instantiateCache() throws CacheException {
		if (cache == null) {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
	        cache = cacheFactory.createCache(Collections.emptyMap());
		}
	}
	
	public static String get(String user) {
		try {
			instantiateCache();
		} catch (CacheException e) {
			return "CacheException: " + e.getMessage();
		}
		String feedData = (String) cache.get(user);
		
		if (feedData != null) return feedData;
		
		Feed feed = get(user, 1, 20);
		String feedString = feed.toString();
		cache.put(user, feedString);
		if (!feed.isEmpty()) return feedString;
		
		return refresh(user);
	}
	
	public static String refresh(String user) {
		Twitter twitter = HiddenData.getTwitter();
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			boolean run = true;
			int page = 1;
			while (run) {
				System.out.println("Getting twitter page " + page);
				List<Status> tweets = twitter.getUserTimeline(user, new Paging(page, 20));
				
				boolean createdObjects = false;
				for (Status tweet : tweets) {
					FeedEntry newEntry = new FeedEntry(user, Long.toString(tweet.getId()), tweet.getText(), tweet.getCreatedAt());
					
					try {
						@SuppressWarnings("unused")
						FeedEntry e = pm.getObjectById(FeedEntry.class, newEntry.tweetId);
					} catch (JDOObjectNotFoundException e) {
						pm.makePersistent(newEntry);
						
						System.out.println("Creating a feed entry");
						createdObjects = true;
					}
				}
				
				if (createdObjects && page++ < MAX_PAGES) {
					createdObjects = false;
				} else {
					run = false;
				}
			}
			System.out.println("Done fetching tweets");
			
			String result = get(user, 1, 20).toString();
			
			instantiateCache();
			
			cache.put(user, result);
			return result;
		} catch (TwitterException e) {
			return "TwitterException: " + e.getMessage();
		} catch (CacheException e) {
			return "CacheException: " + e.getMessage();
		}
	}
	
	private static Feed get(String user, int page, int count) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Feed feed = new Feed();
		
		Query q = pm.newQuery();
		q.setClass(FeedEntry.class);
		q.setFilter("user == " + user);
		q.setOrdering("time descending");
		q.setRange((page - 1) * count, page * count);
		
		@SuppressWarnings("unchecked")
		Collection<FeedEntry> entries = (Collection<FeedEntry>) q.execute();
		for (FeedEntry entry : entries) {
			feed.add(entry);
		}
	
		return feed;
	}
}
