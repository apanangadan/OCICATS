package com.pack.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

	@Override
	public String createURL(String q, String startdate, String enddate) {
		// TODO Auto-generated method stub
		String url = "https://twitter.com/i/search/timeline?vertical=news"
				+ "&q="+q;
		return url;
	}
	
	public void StoreFileToResources(String q, String startdate, String enddate, String path) throws IOException {
		SearchTweets st = new SearchTweets();
		Datasets dataset = new Datasets();
		dataset.tweetsFilter(st.searchTweets(q), path);
	}

	

}
