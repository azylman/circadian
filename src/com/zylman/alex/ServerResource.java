package com.zylman.alex;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class ServerResource extends org.restlet.resource.ServerResource {
	public String getAttribute(String attr) {
		return (String) getRequest().getAttributes().get(attr);
	}
	
	private Representation toRepresentation(Map<String, Object> map, String templateName, MediaType mediaType) {
		return new TemplateRepresentation(
        		templateName,
        		((MeApplication) getApplication()).getConfiguration(),
        		map,
        		mediaType);
	}
	
	public Representation populateTemplate(Map<String, Object> map, String templateName) throws IOException {
        return toRepresentation(map, templateName, MediaType.TEXT_HTML);
    }
	
	public Representation createJSONRepresentation(String text) {
		Representation result = new StringRepresentation(text, MediaType.APPLICATION_JSON);
		
		// Set an expiration date 10 minutes from now.
		Calendar time = new GregorianCalendar();
		time.setTime(new Date());
		time.set(Calendar.MINUTE, time.get(Calendar.MINUTE) + 10);
		result.setExpirationDate(time.getTime());
		
		return result;
	}
}
