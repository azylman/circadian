package com.zylman.alex.profile;

import net.sf.jsr107cache.CacheException;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import com.zylman.alex.HiddenData;
import com.zylman.alex.ServerResource;
import com.zylman.alex.User;

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
