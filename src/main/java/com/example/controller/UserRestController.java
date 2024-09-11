package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;
import com.example.form.UserDetailForm;
import com.example.form.UserListForm;
import com.example.rest.RestResult;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * ユーザー情報の取得処理（複数）
	 * <p>REST検索用</p>
	 * @param user
	 * @return ユーザー情報リスト
	 */
	@GetMapping("/rest/list")
	public List<MUser> getUserList(UserListForm form) {
		MUser user = this.modelMapper.map(form, MUser.class);
		List<MUser> userList = this.userService.getUsers(user);
		return userList;
	}
	
	/**
	 * ユーザー登録処理
	 * @param form 登録フォーム
	 * @param bindingResult
	 * @param locale
	 * @return 成功の場合0、エラーの場合90
	 */
	@PostMapping("/signup/rest")
	public RestResult postSignup(@Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult, Locale locale) {
		// 入力チェック
		if(bindingResult.hasErrors()) {
			// エラー情報格納用Mapオブジェクト
			Map<String, String> errors = new HashMap<>();
			//　画面上のエラーをMapオブジェクトに格納
			for(FieldError error: bindingResult.getFieldErrors()) {
				String message = this.messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			// 結果の返却（リターンコード：90）
			return new RestResult(90, errors);
		}
		// フォーム --> マップオブジェクトに変換
		/* 画面の変更の影響対策、他画面からもサービスを再利用できるようにするため */
		MUser user = this.modelMapper.map(form, MUser.class);
		//  ユーザー情報の登録処理
		this.userService.signup(user);
		// 結果の返却（リターンコード：0）
		return new RestResult(0, null);
	}
	
	/**
	 * ユーザー情報の更新処理
	 * ユーザーIDにて検索
	 * @param form 更新フォーム
	 * @return 成功なら0
	 */
	@PutMapping("/update")
	public int updateUser(UserDetailForm form) {
		this.userService.updateUserByUserId(form.getUserId(), form.getPassword(), form.getUserName());
		return 0;
	}
	
	/**
	 * ユーザー情報の削除処理
	 * @param form 更新フォーム
	 * @return 成功なら0
	 */
	@DeleteMapping("/delete")
	public int deleteUser(UserDetailForm form) {
		this.userService.logicalDeleteUserByUserId(form.getUserId());
		return 0;
	}
}
