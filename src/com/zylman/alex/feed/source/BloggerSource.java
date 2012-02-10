package com.zylman.alex.feed.source;

import com.zylman.alex.User;

public class BloggerSource extends FeedSource {

	public BloggerSource() {
		super("Blogger", "https://twitter.com/#!/amzylman");
	}
	
	@Override public void refresh(User user) throws FeedException {
		
	}
}
