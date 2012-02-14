package com.zylman.alex.feed.source;

import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.zylman.alex.HiddenData;
import com.zylman.alex.PMF;
import com.zylman.alex.User;
import com.zylman.alex.feed.FeedEntry;

public class TwitterSource extends FeedSource {

	String twitterName;
	
	public TwitterSource(String twitterName) {
		super("Twitter", "https://twitter.com/#!/" + twitterName);
		this.twitterName = twitterName;
	}
	
	@Override public void refresh(User user) throws FeedException {
		Twitter twitter = HiddenData.getTwitter();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		boolean run = true;
		int page = 1;
		
		try {
			while (run) {
				List<Status> tweets = twitter.getUserTimeline(twitterName, new Paging(page, 20));
				
				boolean createdObjects = false;
				for (Status tweet : tweets) {
					FeedEntry newEntry = new FeedEntry(user, tweet);
					newEntry.setSource(getSourceNum());
					
					try {
						@SuppressWarnings("unused")
						FeedEntry e = pm.getObjectById(FeedEntry.class, newEntry.getId());
					} catch (JDOObjectNotFoundException e) {
						pm.makePersistent(newEntry);
						createdObjects = true;
					}
				}
				
				if (createdObjects) {
					page++;
					createdObjects = false;
				} else {
					run = false;
				}
			}
		} catch (TwitterException ex) {
			throw new FeedException(ex);
		}
		
		pm.close();
	}
	
}
