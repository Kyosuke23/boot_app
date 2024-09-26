package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserListForm;

/**
 * 左サイドバーのリンクに関するコントローラークラス
 * @author Kyosuke Ikeda
 *
 */
@Controller
public class MenuController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * アドミン権限専用画面を表示
	 * @return アドミン権限専用画面
	 */
	@GetMapping("/top")
	public String getTop() {
		return "top/top";
	}
	
	/**
	 * ユーザーリスト画面を表示
	 * @param form
	 * @param model
	 * @param pageable
	 * @return ユーザーリスト画面
	 */
	@GetMapping("/user/list")
	public String getUserList(@ModelAttribute UserListForm form, Model model, @PageableDefault(size=10) Pageable pageable) {
		MUser user = this.modelMapper.map(form, MUser.class);
		Page<MUser> userList = this.userService.getUsers(user, pageable);
		model.addAttribute("page", userList);
		return "user/list";
	}
	
	/**
	 * ユーザーリスト画面(REST)を表示
	 * @param form
	 * @param model
	 * @param pageable
	 * @return ユーザーリスト画面(REST)
	 */
	@GetMapping("/user/list/rest")
	public String getUserListRest(@ModelAttribute UserListForm form, Model model, @PageableDefault(size=10) Pageable pageable) {
		MUser user = this.modelMapper.map(form, MUser.class);
		Page<MUser> userList = this.userService.getUsers(user, pageable);
		model.addAttribute("page", userList);
		return "user_rest/list";
	}
	
	/**
	 * アドミン権限専用画面を表示
	 * @return アドミン権限専用画面
	 */
	@GetMapping("/admin")
	public String getAdmin() {
		return "admin/admin";
	}
}
