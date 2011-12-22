package com.zylman.alex;

import net.sf.jsr107cache.CacheException;

import org.restlet.resource.Get;

public class LinkedInResource extends ServerResource {
	@Get public String retrieve() {
		String result;

	    try {
            LinkedInHelper.instantiateCache();
            
            result = LinkedInHelper.get("linkedin");
        } catch (CacheException e) {
            result = "CacheException: " + e.getMessage();
        }
	    
	    return result;
	}
}
