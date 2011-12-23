package com.zylman.alex;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class MeApplication extends Application {
	@Override public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/linkedin", LinkedInResource.class);
        router.attach("/linkedin/recache", LinkedInRecacheResource.class);
        router.attach("/twitter", TwitterResource.class);
        router.attach("/twitter/recache", TwitterRecacheResource.class);
        router.attach("/twitter/{pageNum}", TwitterResource.class);
        router.attach("/recache", Recache.class);

        return router;
    }
}
