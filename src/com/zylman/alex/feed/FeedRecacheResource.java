package com.zylman.alex.feed;

import org.restlet.resource.Get;

import com.zylman.alex.HiddenData;
import com.zylman.alex.ServerResource;
import com.zylman.alex.User;

public class FeedRecacheResource extends ServerResource {
	@Get public String retrieve() {
		User user = HiddenData.getAdmin();
		return FeedHelper.refresh(user);
	}
}
