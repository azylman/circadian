package com.zylman.alex.resource;

import net.sf.jsr107cache.CacheException;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import com.zylman.alex.HiddenData;
import com.zylman.alex.User;
import com.zylman.alex.profile.LinkedInHelper;

public class LinkedInRecacheResource extends ServerResource {
	@Get public Representation retrieve() {
		try {
			User user = HiddenData.getAdmin();
			boolean same = LinkedInHelper.refresh(user);
			if (!same) {
				return createJSONRepresentation(LinkedInHelper.get(user).getProfile());
			} else {
				getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
				return new StringRepresentation("");
			}
		} catch (CacheException e) {
			return new StringRepresentation("CacheException: " + e.getMessage());
		}
	}
}
