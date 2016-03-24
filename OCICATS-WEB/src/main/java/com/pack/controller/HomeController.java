package com.pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping({"/", "/home"})
	public String welcome(Model model) {
		model.addAttribute("greeting", "Welcome to OCICATS");
		model.addAttribute("tagline", "Hello");
		return "welcome";
	}
}
