package com.zylman.alex.feed.source;

@SuppressWarnings("serial")
public class FeedException extends Exception {
	public FeedException(Throwable cause) {
		super(cause.getMessage(), cause);
	}
}
