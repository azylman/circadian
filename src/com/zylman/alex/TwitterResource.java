package com.zylman.alex;

import org.restlet.resource.Get;

public class TwitterResource extends ServerResource {
	@Get public String retrieve() {
		User user = HiddenData.getAdmin();
		String pageNum = getAttribute("pageNum");
		if (pageNum == null) return TwitterHelper.get(user);
		return TwitterHelper.get(user, Integer.parseInt(pageNum)).toString();
	}
}