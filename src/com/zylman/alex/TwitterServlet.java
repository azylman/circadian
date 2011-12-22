package com.zylman.alex;

import java.util.List;

import org.restlet.resource.Get;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterServlet extends ServerResource {
	@Get public String retrieve() {
		Twitter twitter = HiddenData.getTwitter();
		try {
			List<Status> tweets = twitter.getUserTimeline("amzylman", new Paging().count(50));
			Feed feed = new Feed();
			for (Status tweet : tweets) {
				feed.add(new FeedEntry(Long.toString(tweet.getId()), tweet.getText(), tweet.getCreatedAt()));
			}
			return feed.toString();
		} catch (TwitterException e) {
			return "TwitterException: " + e.getMessage();
		}
	}
}