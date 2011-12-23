package com.zylman.alex;

import org.restlet.resource.Get;

public class TwitterRecacheResource extends ServerResource {
	@Get public String retrieve() {
		User user = HiddenData.getAdmin();
		return TwitterHelper.refresh(user);
	}
}
