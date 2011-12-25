package com.zylman.alex.feed;

import java.util.Collection;
import java.util.Collections;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.zylman.alex.PMF;
import com.zylman.alex.User;
import com.zylman.alex.feed.source.FeedException;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

public class FeedHelper {
	private static Cache cache = null;
	private static final int ENTRIES_PER_PAGE = 20;
	
	private static void instantiateCache() throws CacheException {
		if (cache == null) {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
	        cache = cacheFactory.createCache(Collections.emptyMap());
		}
	}
	
	public static String get(User user) {
		try {
			instantiateCache();
		} catch (CacheException e) {
			return "CacheException: " + e.getMessage();
		}
		String feedData = (String) cache.get("twitter-" + user.getEmail());
		
		if (feedData != null) return feedData;
		
		Feed feed = get(user, 1);
		String feedString = feed.toString();
		cache.put("twitter-" + user.getEmail(), feedString);
		if (!feed.isEmpty()) return feedString;
		
		try {
			refresh(user);
		} catch (FeedException e) {
			return "FeedException: " + e.getMessage();
		} catch (CacheException e) {
			return "CacheException: " + e.getMessage();
		}
		
		// The cache exists now, refresh.
		return get(user);
	}
	
	public static boolean refresh(User user) throws FeedException, CacheException {
		user.refreshSources();
		
		String result = get(user, 1).toString();
		
		instantiateCache();
		
		String oldResult = (String) cache.get("twitter-" + user.getEmail());
		
		cache.put("twitter-" + user.getEmail(), result);
		
		return result.equals(oldResult);
	}
	
	public static Feed get(User user, int page) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Feed feed = new Feed();
		
		Query q = pm.newQuery();
		q.setClass(FeedEntry.class);
		q.setFilter("user == \"" + user.getEmail() + "\"");
		q.setOrdering("time descending");
		q.setRange((page - 1) * ENTRIES_PER_PAGE, page * ENTRIES_PER_PAGE);
		
		@SuppressWarnings("unchecked")
		Collection<FeedEntry> entries = (Collection<FeedEntry>) q.execute();
		for (FeedEntry entry : entries) {
			feed.add(entry);
		}
	
		return feed;
	}
}
