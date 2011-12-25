package com.zylman.alex.feed;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import com.zylman.alex.HiddenData;
import com.zylman.alex.ServerResource;
import com.zylman.alex.User;

public class FeedResource extends ServerResource {
	@Get public Representation retrieve() {
		User user = HiddenData.getAdmin();
		String pageNumAttr = getAttribute("pageNum");
		int pageNum = pageNumAttr == null || pageNumAttr.equals("1") ? 1 : Integer.parseInt(pageNumAttr);
		if (pageNum == 1) return createJSONRepresentation(FeedHelper.get(user));
		return createJSONRepresentation(FeedHelper.get(user, pageNum).toString());
	}
}