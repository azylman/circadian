package com.zylman.alex.profile;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.zylman.alex.User;

@PersistenceCapable
public class LinkedInProfile {
	@PrimaryKey
	@Persistent
	private String user;
	
	@Persistent
	private Text profile;
	
	LinkedInProfile(User user, String profile) {
		this.user = user.email;
		this.profile = new Text(profile);
	}
	
	public String getUser() {
		return user;
	}
	
	public String getProfile() {
		return profile.getValue();
	}
}
