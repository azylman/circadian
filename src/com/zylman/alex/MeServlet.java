package com.zylman.alex;

import org.restlet.resource.Get;

public class MeServlet extends ServerResource {
	@Get public String retrieve() {
		return "Hello, world!";
	}
}
