package com.zylman.alex.resource;

import org.restlet.resource.Get;


public class KeepAliveResource extends ServerResource {
	@Get public String retrieve() {
		return "Refreshing all caches...";
	}
}
