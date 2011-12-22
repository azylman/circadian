package com.zylman.alex;

import net.sf.jsr107cache.CacheException;

import org.restlet.resource.Get;

public class LinkedInResource extends ServerResource {
	@Get public String retrieve() {
		try {
			return LinkedInHelper.get("linkedin").getProfile();
		} catch (CacheException e) {
			return "CacheException: " + e.getMessage();
		}
	}
}
