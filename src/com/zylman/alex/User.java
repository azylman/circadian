package com.zylman.alex;

import org.scribe.model.Token;

public class User {
	public String email;
	public String twitterID;
	public String linkedInToken;
	public String linkedInSecret;
	
	public Token getLinkedInToken() {
		return new Token(linkedInSecret, linkedInToken);
	}
	
	public String getTwitterID() {
		return twitterID;
	}
	
	public String getEmail() {
		return email;
	}
}
