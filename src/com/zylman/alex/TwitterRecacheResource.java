package com.zylman.alex;

import org.restlet.resource.Get;

public class TwitterRecacheResource extends ServerResource {
	@Get public String retrieve() {
		return TwitterHelper.refresh("amzylman");
	}
}
