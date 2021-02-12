package com.ps.springbootsampleapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pocAPI")
public class POCApi {
	
	@GetMapping("/msg")
	public String msg() {
		return "I am the first method in this class";
	}
	
}
