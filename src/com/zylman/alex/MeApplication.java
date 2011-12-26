package com.zylman.alex;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.freemarker.ContextTemplateLoader;
import org.restlet.routing.Router;

import com.zylman.alex.feed.FeedRecacheResource;
import com.zylman.alex.feed.FeedResource;
import com.zylman.alex.profile.LinkedInRecacheResource;
import com.zylman.alex.profile.LinkedInResource;

import freemarker.template.Configuration;

public class MeApplication extends Application {
	private Configuration configuration;
	
	@Override public Restlet createInboundRoot() {
		configuration = new Configuration();
		configuration.setTemplateLoader(new ContextTemplateLoader(getContext(), "war:///templates"));
		
        Router router = new Router(getContext());

        router.attach("/", Me.class);
        router.attach("/linkedin", LinkedInResource.class);
        router.attach("/linkedin/recache", LinkedInRecacheResource.class);
        router.attach("/twitter", FeedResource.class);
        router.attach("/twitter/recache", FeedRecacheResource.class);
        router.attach("/twitter/recache/{pageNum}", FeedRecacheResource.class);
        router.attach("/twitter/{pageNum}", FeedResource.class);
        router.attach("/recache", Recache.class);
        router.attach("/{pageNum}", Me.class);

        return router;
    }
	
	public Configuration getConfiguration() {
		return configuration;
	}
}
