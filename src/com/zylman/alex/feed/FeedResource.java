package com.zylman.alex.feed;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.zylman.alex.HiddenData;
import com.zylman.alex.ServerResource;
import com.zylman.alex.User;

public class FeedResource extends ServerResource {
	@Get public Representation retrieve() {
		User user = HiddenData.getAdmin();
		String pageNum = getAttribute("pageNum");
		if (pageNum == null) return createJSONRepresentation(FeedHelper.get(user));
		return createJSONRepresentation(FeedHelper.get(user, Integer.parseInt(pageNum)).toString());
	}
}