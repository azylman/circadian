package com.zylman.alex.resource;

import net.sf.jsr107cache.CacheException;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import com.zylman.alex.HiddenData;
import com.zylman.alex.User;
import com.zylman.alex.feed.FeedHelper;
import com.zylman.alex.feed.source.FeedException;

public class FeedRecacheResource extends ServerResource {
	@Get public Representation retrieve() throws FeedException, CacheException {
		User user = HiddenData.getAdmin();
		boolean same = FeedHelper.refresh(user);
		if (!same) {
			// Return code 200 (default)
			String pageNumAttr = getAttribute("pageNum");
			int pageNum = pageNumAttr == null || pageNumAttr.equals("1") ? 1 : Integer.parseInt(pageNumAttr);
			if (pageNum == 1) return createJSONRepresentation(FeedHelper.get(user));
			return createJSONRepresentation(FeedHelper.get(user, pageNum).toString());
		} else {
			getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
			return new StringRepresentation("");
		}
	}
}
