package com.zylman.alex.blogger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.zylman.alex.HiddenData;
import com.zylman.alex.feed.FeedEntry;

public class BloggerClient {
	public List<BloggerPost> getPosts(String blogId) {
		return getPosts(blogId, null, null);
	}
	
	public List<BloggerPost> getPosts(String blogId, FeedEntry feedEntry) {
		return getPosts(blogId, null, feedEntry);
	}
	
	public List<BloggerPost> getPosts(String blogId, String pageToken, FeedEntry feedEntry) {
		try {
			URL bloggerUrl = constructGetPostsUrl(blogId, pageToken);
			
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			HTTPResponse response = urlFetch.fetch(bloggerUrl);
			
			String responseContent = new String(response.getContent());
			
			JSONObject responseJson = new JSONObject(responseContent);
			
			String nextPage = responseJson.getString("nextPageToken");
			
			List<BloggerPost> posts = extractPostsFromJson(responseJson.getJSONArray("items"));
			
			if (feedEntry != null) {
				for (BloggerPost post : posts) {
					if (FeedEntry.getBloggerId(post).equals(feedEntry.getId())) {
						return posts;
					}
				}
			}
			
			List<BloggerPost> nextPosts = getPosts(blogId, nextPage, feedEntry);
			
			if (nextPosts != null) {
				posts.addAll(nextPosts);
			}
			
			return posts;
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (JSONException e) {
			return null;
		}
	}
	
	private List<BloggerPost> extractPostsFromJson(JSONArray postArray) {
		List<BloggerPost> posts = new ArrayList<BloggerPost>();
		
		for (int i = 0; i < postArray.length(); ++i) {
			try {
				posts.add(new BloggerPost(postArray.getJSONObject(i)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return posts;
	}
	
	private URL constructGetPostsUrl(String blogId, String pageToken) throws MalformedURLException {
		StringBuilder url = new StringBuilder(
				"https://www.googleapis.com/blogger/v2/blogs/1880134641060961962/posts?");
		url.append("fields=items(content%2Cid%2Cpublished%2Ctitle)%2CnextPageToken%2CprevPageToken");
		url.append("&pp=1");
		url.append("&key=");
		url.append(HiddenData.getBloggerAPIKey());
		url.append(pageToken != null ? "&pageToken=" + pageToken : "");
		return new URL(url.toString());
	}
}
