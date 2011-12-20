package com.zylman.alex;

public class ServerResource extends org.restlet.resource.ServerResource {
	String getAttribute(String attr) {
		return (String) getRequest().getAttributes().get(attr);
	}
}
