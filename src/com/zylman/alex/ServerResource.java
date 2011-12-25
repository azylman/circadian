package com.zylman.alex;

import java.io.IOException;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;

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
	protected Representation populateTemplate(Map<String, Object> map, String templateName) throws IOException {
        return toRepresentation(map, templateName, MediaType.TEXT_HTML);
    }
}
