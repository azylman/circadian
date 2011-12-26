package com.zylman.alex;

import org.restlet.data.Cookie;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;

public class BloggerTwo extends ServerResource {
	@Get public Representation retrieve() throws OAuthException {
		Cookie cookie = getRequest().getCookies().getFirst("secret");
		String accessTokenSecret = cookie.getValue();
		
		GoogleOAuthParameters oauthParameters = HiddenData.getBloggerAuthParameters();
		oauthParameters.setOAuthTokenSecret(cookie.getValue());
		
		GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
		String accessToken = oauthHelper.getAccessToken(getQuery().getQueryString(), oauthParameters);
		
		return new StringRepresentation(
				"Access token: " + accessToken + ", access token secret: " + accessTokenSecret,
				MediaType.TEXT_HTML);
	}
}
