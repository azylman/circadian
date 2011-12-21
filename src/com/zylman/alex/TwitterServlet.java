package com.zylman.alex;

import org.restlet.resource.Get;

public class TwitterServlet extends ServerResource {
	@Get public String retrieve() {
		return "Hello, world!";
	}
}
