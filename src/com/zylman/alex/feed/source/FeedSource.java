package com.zylman.alex.feed.source;

import com.zylman.alex.User;

public interface FeedSource {
	void refresh(User user) throws FeedException;
}
