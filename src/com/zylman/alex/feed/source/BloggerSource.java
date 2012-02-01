package com.zylman.alex.feed.source;

import java.net.URL;

import com.google.gdata.client.GoogleService;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.blogger.BloggerService;
import com.google.gdata.data.Entry;
import com.google.gdata.data.Feed;
import com.zylman.alex.HiddenData;
import com.zylman.alex.User;

public class BloggerSource extends FeedSource {

	public BloggerSource() {
		super("Blogger", "https://twitter.com/#!/amzylman");
	}
	
	@Override public void refresh(User user) throws FeedException {
		GoogleOAuthParameters oauthParams = HiddenData.getBloggerAuthParametersWithToken();
		GoogleService myService = new BloggerService("zylman-circadian-1");
		try {
			myService.setOAuthCredentials(oauthParams, new OAuthHmacSha1Signer());
		
			URL feedUrl = new URL("http://www.blogger.com/feeds/default/blogs");
			Feed resultFeed = myService.getFeed(feedUrl, Feed.class);

			// Print the results
			System.out.println(resultFeed.getTitle().getPlainText());
			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				Entry entry = resultFeed.getEntries().get(i);
				System.out.println("\t" + entry.getTitle().getPlainText());
			}
		} catch (Exception e) {
			throw new FeedException(e);
		}
	}
}
