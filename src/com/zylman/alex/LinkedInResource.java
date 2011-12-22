package com.zylman.alex;

import net.sf.jsr107cache.CacheException;

import org.restlet.resource.Get;

public class LinkedInResource extends ServerResource {
	@Get public String retrieve() {
		String result;

	    try {
            LinkedInHelper.instantiateCache();
            
            result = LinkedInHelper.get("linkedin").getProfile();
        } catch (CacheException e) {
            result = "CacheException: " + e.getMessage();
        }
	    
	    System.out.println("Outputting profile...");
	    return result;
	}
}
