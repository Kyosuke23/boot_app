package com.example.domain.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;

/**
 * ユーザー認証サービス
 * @author Kyosuke Ikeda
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService service;

	/**
	 * ユーザー認証処理
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// ログインユーザー情報の取得
		MUser loginUser = this.service.getLoginUser(username);
		// ログインユーザーを取得できなければエラー
		if(loginUser == null) {
			throw new UsernameNotFoundException("user not found");
		}
		// 権限リストの生成
		GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);
		// 認証情報でUserDetailsを生成
		UserDetails userDetails = (UserDetails) new User(loginUser.getUserId(), loginUser.getPassword(), authorities);
		return userDetails;
	}

}
