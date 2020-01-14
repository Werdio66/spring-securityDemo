package com.atguigu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GongfuController {
	
	@RequestMapping("/level1/{path}")
	public String leve1Page(@PathVariable("path")String path){
		return "level1/" + path;
	}
	
	@RequestMapping("/level2/{path}")
	public String leve2Page(@PathVariable("path")String path){
		return "level2/"+path;
	}
	
	@RequestMapping("/level3/{path}")
	public String leve3Page(@PathVariable("path")String path){
		return "level3/"+path;
	}

}
