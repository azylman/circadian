package com.zylman.alex;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class MeServlet extends ServerResource {
	@Get
	public String retrieve() {
		return "Hello, world";
	}
}
