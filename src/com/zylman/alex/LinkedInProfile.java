package com.zylman.alex;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class LinkedInProfile {
	@PrimaryKey
	@Persistent
	private String user;
	
	@Persistent
	private Text profile;
	
	LinkedInProfile(String user, String profile) {
		this.user = user;
		this.profile = new Text(profile);
	}
	
	public String getUser() {
		return user;
	}
	
	public String getProfile() {
		return profile.getValue();
	}
}
