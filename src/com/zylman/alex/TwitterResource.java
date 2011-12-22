package com.zylman.alex;

import org.restlet.resource.Get;

public class TwitterResource extends ServerResource {
	@Get public String retrieve() {
		return TwitterHelper.get("amzylman");
	}
}