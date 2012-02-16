package com.zylman.alex;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.freemarker.ContextTemplateLoader;
import org.restlet.routing.Router;

import com.zylman.alex.resource.FeedRecacheResource;
import com.zylman.alex.resource.FeedResource;
import com.zylman.alex.resource.LinkedInRecacheResource;
import com.zylman.alex.resource.LinkedInResource;
import com.zylman.alex.resource.Me;
import com.zylman.alex.resource.Recache;

import freemarker.template.Configuration;

public class MeApplication extends Application {
	private Configuration configuration;
	
	@Override public Restlet createInboundRoot() {
		configuration = new Configuration();
		configuration.setTemplateLoader(new ContextTemplateLoader(getContext(), "war:///templates"));
		
        Router router = new Router(getContext());

        router.attach("/", Me.class);
        router.attach("/profile", LinkedInResource.class);
        router.attach("/profile/recache", LinkedInRecacheResource.class);
        router.attach("/feed", FeedResource.class);
        router.attach("/feed/recache", FeedRecacheResource.class);
        router.attach("/feed/recache/{pageNum}", FeedRecacheResource.class);
        router.attach("/feed/{pageNum}", FeedResource.class);
        router.attach("/recache", Recache.class);
        
        // MUST BE LAST
        router.attach("/{pageNum}", Me.class);

        return router;
    }
	
	public Configuration getConfiguration() {
		return configuration;
	}
}
