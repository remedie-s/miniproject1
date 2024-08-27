package com.example.mini.controller;

import java.time.Duration;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mini.config.JwtProperties;
import com.example.mini.dto.SpUserForm;
import com.example.mini.entity.SpUser;
import com.example.mini.service.SpUserService;
import com.example.mini.service.TokenProvider;
import com.example.mini.service.TokenService;
import com.example.mini.service.UtilService;

import groovy.lang.Binding;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequestMapping("/spuser")
@RequiredArgsConstructor
@Controller
public class SpUserController {
	private final SpUserService spUserService;
	private final TokenProvider tokenProvider;
	private final TokenService tokenService;
	private final JwtProperties jwtProperties;
	private final UtilService utilService;
	
	@GetMapping("/signup")
	public String sighup(@Valid SpUserForm spUserForm, BindingResult bindingResult, HttpServletResponse response ) {
		System.out.println("가입요청");
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		System.out.println("비번체크");
		if(!spUserForm.getPassword1().equals(spUserForm.getPassword2())) {
			bindingResult.rejectValue("password", "passwordInCorrect", "비밀번호가 서로다릅니다.");
			return "signup_form";
		}
		SpUser spUser;
		try {
			System.out.println("회원가입 진행");
			spUser=this.spUserService.create(spUserForm.getSpuser_name(), spUserForm.getPassword1(), 
											spUserForm.getPassword2(), spUserForm.getFirst_name(), 
											spUserForm.getLast_name(), spUserForm.getPhone_number(),
											spUserForm.getE_mail());
			System.out.println("회원가입 완료");
		} catch (DataIntegrityViolationException e) {
			bindingResult.reject("signupError","이미 사용중인 회원정보입니다.");
			return "signup_form";
		} catch (Exception e) {
			bindingResult.reject("signupError","회원가입 오류입니다.");
			return "signup_form";
		}
		String accessToken = this.tokenProvider.geneateToken(spUser, Duration.ofDays(7));		
		String refreshToken = this.tokenProvider.geneateToken(spUser, Duration.ofDays(30));
		
		this.tokenService.saveRefreshToken(spUser.getId(), refreshToken);
		utilService.setCookie("access_token", accessToken, utilService.toSecondOfDay(7),response);
		utilService.setCookie("refresh_token", refreshToken, utilService.toSecondOfDay(30),response);
		
		return "redirect:/spuser/login";
	}
	//TODO 해야함
	
	
	
	
	

}
