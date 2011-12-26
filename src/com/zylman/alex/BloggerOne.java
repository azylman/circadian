package com.zylman.alex;

import org.restlet.data.CookieSetting;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import com.google.gdata.client.authn.oauth.*;

public class BloggerOne extends ServerResource {
	
	@Get public Representation retrieve() throws OAuthException {
		
		// --- Fetch the request token ---
		GoogleOAuthParameters oauthParameters = HiddenData.getBloggerAuthParameters();
		oauthParameters.setScope("http://www.blogger.com/feeds/");
		oauthParameters.setOAuthCallback("http://localhost:8888/blogger2");
	
		GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
		oauthHelper.getUnauthorizedRequestToken(oauthParameters);
		
		String tokenSecret = oauthParameters.getOAuthTokenSecret();
		
		// --- Authorize the request token ---
		String approvalPageUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
		
		CookieSetting cookie = new CookieSetting("secret", tokenSecret);
		getResponse().getCookieSettings().add(cookie);
		
		return new StringRepresentation(
				"Click <a href='" + approvalPageUrl + "'>here</a> to approve the request",
				MediaType.TEXT_HTML);
	}
}
