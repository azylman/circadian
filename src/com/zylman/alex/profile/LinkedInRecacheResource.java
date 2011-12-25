package com.zylman.alex.profile;

import net.sf.jsr107cache.CacheException;

import org.restlet.resource.Get;

import com.zylman.alex.HiddenData;
import com.zylman.alex.ServerResource;
import com.zylman.alex.User;

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
