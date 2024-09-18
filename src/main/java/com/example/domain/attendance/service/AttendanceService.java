package com.example.domain.attendance.service;

import java.util.List;

import com.example.domain.attendance.model.Attendance;
import com.example.domain.attendance.model.WorkingType;

/**
 * 勤怠処理に関するサービスクラス
 * @author Kyosuke
 *
 */
public interface AttendanceService {

	/**
	 * 勤怠情報の取得処理
	 * @param userId ユーザーID
	 * @param startDate　開始日
	 * @param endDate 終了日
	 * @return　ユーザーIDに関連する勤怠情報
	 */
	public List<Attendance> selectAttendanceByUserId(String userId, String startDate, String endDate);
	
	/**
	 * 勤務区分マスタ情報の取得処理
	 * @return 勤務区分マスタ情報
	 */
	public List<WorkingType> getAllWorkingType();
	
	/**
	 * 勤怠情報の登録処理
	 * @param userId ユーザーID
	 * @param attendanceYm　勤怠情報の年月
	 * @param status 勤怠情報のステータス
	 */
	public void upsertAttendance(String userId, String attendanceYm, Integer status);
	
	/**
	 * 勤怠詳細情報の登録処理
	 * @param userId ユーザーID
	 * @param attendanceList 勤怠詳細情報リスト
	 */
	public void upsertAttendanceDetail(String userId, List<Attendance> attendanceList);
}
