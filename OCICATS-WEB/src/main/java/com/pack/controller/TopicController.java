package com.pack.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pack.service.TopicService;


@Controller
public class TopicController {
	String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping("/topics")
	public String topicpage() {
		return "cart";
	}
	
	@RequestMapping(value="/topics/list", method = RequestMethod.GET)
	public ResponseEntity<List> getFilesList(HttpServletRequest request) {
		String str = request.getSession().getServletContext().getRealPath("/");
		//System.out.println(str);
		List rs = topicService.searchFiles(str);
		return new ResponseEntity<List>(rs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/topics/analyze", method = RequestMethod.POST)
	public ResponseEntity<Void> analyze(@RequestBody String filename,HttpServletRequest request) {
		String str = request.getSession().getServletContext().getRealPath("/")+"resources/files/"+filename;
		//System.out.println(str);
		String defaultPath = request.getSession().getServletContext().getRealPath("/")+"resources/topic/";
		String s = topicService.LDA(str,defaultPath);
		path = s;
		return new ResponseEntity<Void>( HttpStatus.OK);
	}
	
	@RequestMapping(value="/topics/path", method = RequestMethod.GET)
	public ResponseEntity<Object> getServicePath(HttpServletRequest request) {
		Object str = "resources/topic/"+path+".txt";
		System.out.println(str);
		return new ResponseEntity<Object>(str, HttpStatus.OK);
	}
	
	
}
