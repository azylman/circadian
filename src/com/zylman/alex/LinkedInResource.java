package com.zylman.alex;

import net.sf.jsr107cache.CacheException;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

public class LinkedInResource extends ServerResource {
	@Get public String retrieve() {
		String result;

	    try {
            LinkedInHelper.instantiateCache();
            
            result = LinkedInHelper.get("linkedin");
            if (result == null) {
            	result = LinkedInHelper.refresh("linkedin");
            }
        } catch (CacheException e) {
            result = "CacheException: " + e.getMessage();
        }
	    
	    return result;
	}
	
	@Put public String update() {
		return LinkedInHelper.refresh("linkedin");
	}
}
