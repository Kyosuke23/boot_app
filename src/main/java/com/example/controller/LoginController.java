package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	/**
	 * ログイン画面を表示
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String getLogin() {
		// ログイン画面に遷移
		return "login/login";
	}
	
	/**
	 * 
	 * @return
	 */
	@PostMapping("/login")
	public String postLogin() {
		return "redirect:/user/list";
	}
}
