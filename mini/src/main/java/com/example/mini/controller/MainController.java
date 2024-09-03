package com.example.mini.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@GetMapping("/index")
	public String main(Principal principal, Model model) {
		if(principal==null){
			return "login_form";
		}
		String name = principal.getName();
		System.out.println("로그인한 유저의 아이디는 : " + name);
		model.addAttribute("name", name);
		return "index";
	}

	@RequestMapping("/")
	public String login() {

		return "login_form";
	}

}
