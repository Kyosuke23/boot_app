package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserDetailForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserDetailController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * ユーザー情報を取得 
	 * @param form
	 * @param model
	 * @param userId
	 * @return
	 */
	@GetMapping("/detail/{userId}")
	public String getUser(UserDetailForm form, Model model, @PathVariable("userId") String userId) {
		MUser user = this.userService.getUserByUserId(userId);
		user.setPassword(null);
		form = this.modelMapper.map(user, UserDetailForm.class);
		form.setSalaryList(user.getSalaryList());
		model.addAttribute("userDetailForm", form);
		return "user/detail";
	}
	
	/**
	 * ユーザー情報の更新処理
	 * @param form
	 * @param model
	 * @return
	 */
	@PostMapping(value="/detail", params="update")
	public String updateUser(UserDetailForm form, Model model) {
		try {
			this.userService.updateUserByUserId(form.getUserId(), form.getPassword(), form.getUserName());
		} catch(Exception e) {
			log.error("ユーザー更新でエラー", e);
		}
		return "redirect:/user/list";
	}
	
	/**
	 * ユーザー情報の削除処理
	 * @param form
	 * @param model
	 * @return
	 */
	@PostMapping(value="/detail", params="delete")
	public String deleteUser(UserDetailForm form, Model model) {
		this.userService.logicalDeleteUserByUserId(form.getUserId());
		return "redirect:/user/list";
	}
}
