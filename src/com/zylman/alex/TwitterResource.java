package com.zylman.alex;

import net.sf.jsr107cache.CacheException;

import org.restlet.resource.Get;

public class TwitterResource extends ServerResource {
	@Get public String retrieve() {
		try {
			TwitterHelper.instantiateCache();
			return TwitterHelper.get("amzylman");
		} catch (CacheException e) {
			return "CacheException: " + e.getMessage();
		}
	}
}