package com.zylman.alex.feed;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Feed {
	private List<FeedEntry> entries = new ArrayList<FeedEntry>();
	
	public void add(FeedEntry entry) {
		entries.add(entry);
	}
	
	@Override public String toString() {
		Gson gson = new Gson();
		return gson.toJson(entries);
	}
	
	public boolean isEmpty() {
		return entries.isEmpty();
	}
	
	public List<FeedEntry> getEntries() {
		return entries;
	}
}
