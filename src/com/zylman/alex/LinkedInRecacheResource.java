package com.zylman.alex;

import net.sf.jsr107cache.CacheException;

import org.restlet.resource.Get;

public class LinkedInRecacheResource extends ServerResource {
	@Get public String retrieve() {
		try {
			User user = HiddenData.getAdmin();
			return LinkedInHelper.refresh(user).getProfile();
		} catch (CacheException e) {
			return "CacheException: " + e.getMessage();
		}
	}
}
