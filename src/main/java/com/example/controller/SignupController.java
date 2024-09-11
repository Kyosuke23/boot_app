package com.example.controller;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.application.service.UserApplicationService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {

	@Autowired
	private UserApplicationService userApplicationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * ユーザー登録画面を表示
	 * @param model
	 * @param locale
	 * @param form
	 * @return ユーザー登録画面
	 */
	@GetMapping("/signup")
	public String getSignup(Model model, Locale locale, @ModelAttribute SignupForm form) {
		// 性別のマップオブジェクトを取得
		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);
		// ユーザー登録画面に遷移
		return "user/signup";
	}
	
	/**
	 * ユーザー情報の登録処理
	 * @param model
	 * @param locale
	 * @param form
	 * @param bindingResult
	 * @return ログイン画面にリダイレクト
	 */
	@PostMapping("/signup")
	public String postSignup(Model model, Locale locale
			, @ModelAttribute @Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult) {
		// 入力チェック
		if(bindingResult.hasErrors()) {
			// エラー時はユーザー登録画面を再表示
			return getSignup(model, locale, form);
		}
		// 入力情報を出力
		log.info(form.toString());
		// フォーム --> マップオブジェクトに変換
		/* 画面の変更の影響対策、他画面からもサービスを再利用できるようにするため */
		MUser user = this.modelMapper.map(form, MUser.class);
		//  ユーザー情報の登録処理
		this.userService.signup(user);
		// ログイン画面にリダイレクト
		return "redirect:/login";
	}
	
	/**
	 * データベースエラー発生時の処理
	 * @param e
	 * @param model
	 * @return
	 */
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		model.addAttribute("error", "");
		model.addAttribute("message", "SignupControllerで例外が発生しました");
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		return "error";
	}

	/**
	 * その他例外発生時の処理
	 * @param e
	 * @param model
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {
		model.addAttribute("error", "");
		model.addAttribute("message", "SignupControllerで例外が発生しました");
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		return "error";
	}
}
