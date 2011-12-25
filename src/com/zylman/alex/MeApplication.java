package com.zylman.alex;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.freemarker.ContextTemplateLoader;
import org.restlet.routing.Router;

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
        router.attach("/twitter", TwitterResource.class);
        router.attach("/twitter/recache", TwitterRecacheResource.class);
        router.attach("/twitter/{pageNum}", TwitterResource.class);
        router.attach("/recache", Recache.class);

        return router;
    }
	
	public Configuration getConfiguration() {
		return configuration;
	}
}
