package com.zylman.alex;

import net.sf.jsr107cache.CacheException;

import org.restlet.resource.Get;

public class LinkedInServlet extends ServerResource {
	@Get public String retrieve() {
		String result;

	    try {
            LinkedInCache cache = new LinkedInCache();
            
            result = cache.get("linkedin");
            if (result == null) {
            	result = cache.refresh("linkedin");
            }
        } catch (CacheException e) {
            result = "CacheException: " + e.getMessage();
        }
	    
	    return result;
	}
}
