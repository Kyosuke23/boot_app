package com.example.domain.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.domain.user.model.MUser;

/**
 * ユーザー
 * @author Kyosuke
 *
 */
public interface UserService {
	/**
	 * ユーザー情報の登録処理
	 * @param user
	 */
	public void signup(MUser user);

	/**
	 * ユーザー情報の取得処理（複数）
	 * <p>REST検索用</p>
	 * @param user
	 * @return ユーザー情報リスト
	 */
	public List<MUser> getUsers(MUser user);
	
	/**
	 * ユーザー情報の取得処理（複数）
	 * @param user
	 * @param pageable
	 * @return ユーザー情報リスト
	 */
	public Page<MUser> getUsers(MUser user, Pageable pageable);

	/**
	 * ユーザー情報の取得処理（単体）
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public MUser getUserByUserId(String userId);
	
	/**
	 * ユーザー情報の更新処理
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @param password パスワード
	 * @param userName ユーザー名
	 */
	public void updateUserByUserId(String userId, String password, String userName);
	
	/**
	 * ユーザー情報の論理削除処理
	 * @param userId ユーザーID
	 * @return
	 */
	public void logicalDeleteUserByUserId(String userId);
	
	/**
	 * ユーザー情報の物理削除処理
	 * @param userId ユーザーID
	 * @return
	 */
	public void fisicalDeleteUserByUserId(String userId);
	
	/**
	 * ログインユーザー情報の取得処理（単体）
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public MUser getLoginUser(String userId);
}
