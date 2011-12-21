package com.zylman.alex;

import java.util.Scanner;

import org.restlet.resource.Get;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class LinkedInServlet extends ServerResource {
	private static final String PROTECTED_RESOURCE_URL = "http://api.linkedin.com/v1/people/~/connections:(id,last-name)";
	
	@Get public String retrieve() {
	    OAuthService service = new ServiceBuilder()
	                                .provider(LinkedInApi.class)
	                                .apiKey(HiddenData.getApiKey())
	                                .apiSecret(HiddenData.getApiSecret())
	                                .build();   
	    StringBuilder result = new StringBuilder();
	    
	    result.append("=== LinkedIn's OAuth Workflow ===");
	    result.append("<br />");

	    /*
	    // Obtain the Request Token
	    result.append("Fetching the Request Token...");
	    Token requestToken = service.getRequestToken();
	    result.append("Got the Request Token!");
	    result.append("<br />");

	    Scanner in = new Scanner(System.in);
	    result.append("Now go and authorize Circadian here:");
	    result.append(service.getAuthorizationUrl(requestToken));
	    result.append("And paste the verifier here");
	    System.out.print(">>");
	    Verifier verifier = new Verifier(in.nextLine());
	    result.append("<br />");
	
	    // Trade the Request Token and Verfier for the Access Token
	    result.append("Trading the Request Token for an Access Token...");
	    Token accessToken = service.getAccessToken(requestToken, verifier);
	    */
	    Token accessToken = HiddenData.getToken();
	    result.append("Got the Access Token!");
	    result.append("(if your curious it looks like this: " + accessToken + " )");
	    result.append("<br />");
	
	    // Now let's go and ask for a protected resource!
	    result.append("Now we're going to access a protected resource...");
	    OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    result.append("Got it! Lets see what we found...");
	    result.append("<br />");
	    result.append(response.getBody());
	
	    result.append("<br />");
	    result.append("Thats it man! Go and build something awesome with Scribe! :)");
	    return result.toString();
	}
}
