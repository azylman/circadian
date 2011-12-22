package com.zylman.alex;

import java.util.Collections;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

public class LinkedInHelper {
	private static Cache cache = null;
	private static final String PROFILE_URL = "http://api.linkedin.com/v1/people/~:("
			+ "first-name,last-name,headline,location:(name),industry,summary,specialties,honors,interests,"
			+ "positions,publications,patents,languages,skills,certifications,educations,picture-url,public-profile-url"
			+ ")";
	
	public static void instantiateCache() throws CacheException {
		if (cache == null) {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
	        cache = cacheFactory.createCache(Collections.emptyMap());
		}
	}
	
	public static LinkedInProfile get(String user) throws CacheException {
		instantiateCache();
			
		String profileData = (String) cache.get(user);
		
		if (profileData != null) return new LinkedInProfile(user, profileData);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		LinkedInProfile profile;
		try {
			LinkedInProfile storedProfile = pm.getObjectById(LinkedInProfile.class, user);
			profile = storedProfile;
		} catch (JDOObjectNotFoundException e) {
			profile = refresh(user);
		} finally {
			pm.close();
		}
		
		cache.put(user, profile.getProfile());
		return profile;
	}
	
	public static LinkedInProfile refresh(String user) throws CacheException {
		instantiateCache();
		
		String result = getFreshData(user);
		cache.put("linkedin", result);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(new LinkedInProfile(user, result));
		} finally {
			pm.close();
		}
		
		return new LinkedInProfile(user, result);
	}
	
	private static String getFreshData(String user) {
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
		
		return result.toString();
	}
}
