package com.zylman.alex;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import com.zylman.alex.feed.source.BloggerSource;
import com.zylman.alex.feed.source.FeedException;
import com.zylman.alex.feed.source.FeedSource;

public class BloggerFeed extends ServerResource {
	@Get public Representation retrieve() throws FeedException {
		User user = HiddenData.getAdmin();
		FeedSource feedSource = new BloggerSource();
		feedSource.refresh(user);
		
		return new StringRepresentation("Trololol");
	}
}
