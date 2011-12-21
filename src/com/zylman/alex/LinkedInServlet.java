package com.zylman.alex;

import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
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
	private static final String PROFILE_URL = "http://api.linkedin.com/v1/people/~:("
			+ "first-name,last-name,headline,location:(name),industry,summary,specialties,honors,interests,"
			+ "positions,publications,patents,languages,skills,certifications,educations,picture-url,public-profile-url"
			+ ")";
	
	@Get public String retrieve() {
	    OAuthService service = new ServiceBuilder()
	                                .provider(LinkedInApi.class)
	                                .apiKey(HiddenData.getApiKey())
	                                .apiSecret(HiddenData.getApiSecret())
	                                .build();   
	    StringBuilder result = new StringBuilder();

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
	    
	    OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_URL);
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    
	    try {
            JSONObject xmlJSONObj = XML.toJSONObject(response.getBody());
            String jsonPrettyPrintString = xmlJSONObj.toString(4);
            result.append(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
	    
	    //result.append(response.getBody());
	    return result.toString();
	}
}
