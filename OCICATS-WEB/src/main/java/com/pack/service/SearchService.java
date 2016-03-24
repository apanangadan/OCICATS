package com.pack.service;

import java.io.IOException;

public interface SearchService {

	public String createURL(String q, String startdate, String enddate);
	
	public void StoreFileToResources(String q, String startdate, String enddate, String path) throws IOException;
	
}
