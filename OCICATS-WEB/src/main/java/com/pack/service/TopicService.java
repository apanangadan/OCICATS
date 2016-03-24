package com.pack.service;

import java.util.List;

import org.springframework.stereotype.Service;


public interface TopicService {
	
	
	public  List<String>  searchFiles(String path);
	
	public String LDA(String path, String defaultPath);
	
}
