package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserListForm;

@Controller
@RequestMapping("/user")
public class UserListController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * 検索処理
	 * @param form
	 * @param model
	 * @param pageable
	 * @return ユーザーリスト画面
	 */
	@PostMapping("/list")
	public String PostUserList(@ModelAttribute UserListForm form, Model model, @PageableDefault(size=10) Pageable pageable) {
		MUser user = this.modelMapper.map(form, MUser.class);
		Page<MUser> userList = this.userService.getUsers(user, pageable);
		model.addAttribute("page", userList);
		return "user/list";
	}
}
