package com.example.domain.user.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.repository.UserMapper;

/**
 * ユーザーサービスの実装クラス
 * @author Kyosuke Ikeda
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	/**
	 * ユーザー情報の登録処理
	 * @param user
	 * @return
	 */
	@Override
	public void signup(MUser user) {
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");
		String rawPassword = user.getPassword();
		user.setPassword(this.encoder.encode(rawPassword));
		this.mapper.insertOne(user);
	}
	
	/**
	 * ユーザー情報の取得処理（複数）
	 * <p>REST検索用</p>
	 * @param user
	 * @return ユーザー情報リスト
	 */
	@Override
	public List<MUser> getUsers(MUser user) {
		List<MUser> users = this.mapper.findMany(user);
		return users;
	}
	
	/**
	 * ユーザー情報の取得処理（複数）
	 * @param user
	 * @param pageable
	 * @return ユーザー情報リスト
	 */
	@Override
	public Page<MUser> getUsers(MUser user, Pageable pageable) {
		RowBounds rowBounds = new RowBounds((int)pageable.getOffset(), pageable.getPageSize());
		List<MUser> users = this.mapper.findMany(rowBounds);
		Long total = this.mapper.count();
		return new PageImpl<>(users, pageable, total);
	}
	
	/**
	 * ユーザー情報の取得処理（単体）
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	@Override
	public MUser getUserByUserId(String userId) {
		return this.mapper.findByUserId(userId);
	}
	
	/**
	 * ユーザー情報の更新処理
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @param password パスワード
	 * @param userName ユーザー名
	 */
	@Transactional
	@Override
	public void updateUserByUserId(String userId, String password, String userNamne) {
		String encryptPassword = this.encoder.encode(password);
		this.mapper.updateByUserId(userId, encryptPassword, userNamne);
		this.mapper.updateByUserId(userId, password, userNamne);
	}
	
	/**
	 * ユーザー情報の論理削除処理
	 * @param userId ユーザーID
	 */
	@Override
	public void logicalDeleteUserByUserId(String userId) {
		this.mapper.logicalDeleteByUserId(userId);
	}
	
	/**
	 * ユーザー情報の物理削除処理
	 * @param userId ユーザーID
	 */
	@Override
	public void fisicalDeleteUserByUserId(String userId) {
		this.mapper.fisicalDeleteByUserId(userId);
	}
	
	/**
	 * ログインユーザー情報の取得処理（単体）
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	@Override
	public MUser getLoginUser(String userId) {
		return this.mapper.findLoginUser(userId);
	}
}
