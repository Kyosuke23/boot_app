package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.example.domain.user.model.MUser;
/**
 * MyBatis用のマッパークラス
 * ユーザー
 * @author Kyosuke Ikeda
 *
 */
@Mapper
public interface UserMapper {
	
	Long count();
	
	/**
	 * ユーザー情報の登録処理
	 * @param user
	 * @return
	 */
	public int insertOne(MUser user);
	
	/**
	 * ユーザー情報の取得処理（複数）
	 * <p>REST検索用</p>
	 * @param user
	 * @return ユーザー情報リスト
	 */
	public List<MUser> findMany(MUser user);
	
	/**
	 * ユーザー情報の取得処理（複数）
	 * @param rowBounds
	 * @return ユーザー情報リスト
	 */
	public List<MUser> findMany(RowBounds rowBounds);
	
	/**
	 * ユーザー情報の取得処理（単体）
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public MUser findByUserId(String userId);
	
	/**
	 * ユーザー情報の更新処理
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @param password パスワード
	 * @param userName ユーザー名
	 */
	public void updateByUserId(
			@Param("userId") String userId
			, @Param("password") String password
			, @Param("userName") String userName);
	
	/**
	 * ユーザー情報の論理削除処理
	 * @param userId ユーザーID
	 * @return
	 */
	public int logicalDeleteByUserId(@Param("userId") String userId);
	
	/**
	 * ユーザー情報の物理削除処理
	 * @param userId ユーザーID
	 * @return
	 */
	public int fisicalDeleteByUserId(@Param("userId") String userId);
	
	/**
	 * ログインユーザー情報の取得処理（単体）
	 * ユーザーIDにて検索
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public MUser findLoginUser(String userId);
}
