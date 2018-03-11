package com.dev.mirco.iotcloud;

public class RequestDescriptor {
	
	private String server,resource;
	private boolean observing;
	
	public RequestDescriptor() {}
	public RequestDescriptor(String server, String resource, boolean observing) {
		this.server=server;
		this.resource=resource;
		this.observing=observing;
	}
	
	public RequestDescriptor(String URI) {		
		String pattern = "/([^/]+)/([^/]+)/(\\S+)\\?(\\S+)";
		URI=URI.replaceAll(pattern, "$1 $2 $3 $4");		
		//Output: 0.HTTPServerName  1.CoAP Server  2.Resource  3.Query string
		String[] splittedURI = URI.split("\\s");		
		//Query string splitting
		String[] splittedQuery = splittedURI[3].split("&");

		this.server=splittedURI[1];
		this.resource=splittedURI[2];		
		if(splittedQuery[0].equals("observing=true")) this.observing=true;
		else if(splittedQuery[0].equals("observing=false")) this.observing=false;		
	}
	
	public String getServerName() {
		return this.server;
	}
	public String getResource() {
		return this.resource;
	}
	public boolean isObserving() {
		return this.observing;
	}
	public void setServerName(String serverName) {
		this.server=serverName;
	}
	public void setResource(String resource) {
		this.resource=resource;
	}
	public void setObserving(boolean observing) {
		this.observing=observing;
	}

}
