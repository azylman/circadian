package com.zylman.alex;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import com.zylman.alex.feed.FeedHelper;
import com.zylman.alex.feed.source.BloggerSource;
import com.zylman.alex.feed.source.FeedException;

public class BloggerFeed extends ServerResource {
	@Get public Representation retrieve() throws FeedException, IOException {
		User user = HiddenData.getAdmin();
		BloggerSource feedSource = new BloggerSource("1880134641060961962", "http://blog.alex.zylman.com");
		feedSource.refresh(user);
		
		return new StringRepresentation(createJSONRepresentation(FeedHelper.get(user, 1).toString()).getText());
	}
}
