package com.zylman.alex.resource;

import net.sf.jsr107cache.CacheException;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import com.zylman.alex.HiddenData;
import com.zylman.alex.User;
import com.zylman.alex.profile.LinkedInHelper;

public class LinkedInResource extends ServerResource {
	@Get public Representation retrieve() {
		try {
			User user = HiddenData.getAdmin();
			return createJSONRepresentation(LinkedInHelper.get(user).getProfile());
		} catch (CacheException e) {
			return new StringRepresentation("CacheException: " + e.getMessage());
		}
	}
}
