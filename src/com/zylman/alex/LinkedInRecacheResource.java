package com.zylman.alex;

import org.restlet.resource.Get;

public class LinkedInRecacheResource extends ServerResource {
	@Get public String retrieve() {
		return LinkedInHelper.refresh("linkedin");
	}
}
