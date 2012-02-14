package com.zylman.alex.blogger;

import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

public class BloggerPost {
	
	private String id;
	private String title;
	private String text;
	private Date date;
	
	public BloggerPost(JSONObject post) throws ParseException, JSONException {
		this(post.getString("id"), post.getString("title"), post.getString("content"), post.getString("published"));
	}
	
	public BloggerPost(String id, String title, String text, String date) throws ParseException {
		this.id = id;
		this.title = title;
		this.text = text;
		this.date = DatatypeConverter.parseDateTime(date).getTime();
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getText() {
		return text;
	}
	
	public Date getDate() {
		return date;
	}
	
	class Builder {
		private String id;
		private String title;
		private String text;
		private String date;
		
		Builder() {}
		
		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public Builder setText(String text) {
			this.text = text;
			return this;
		}
		
		public Builder setDate(String date) {
			this.date = date;
			return this;
		}
		
		public BloggerPost build() throws ParseException {
			return new BloggerPost(id, title, text, date);
		}
	}
}
