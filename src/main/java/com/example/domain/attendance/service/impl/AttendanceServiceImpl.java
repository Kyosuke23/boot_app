package com.example.domain.attendance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.attendance.model.Attendance;
import com.example.domain.attendance.model.WorkingType;
import com.example.domain.attendance.service.AttendanceService;
import com.example.repository.AttendanceMapper;

/**
 * 勤怠処理に関するサービスクラス
 * @author Kyosuke Ikeda
 *
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceMapper mapper;

	/**
	 * 勤怠情報の取得処理
	 * @param userId ユーザーID
	 * @param startDate　開始日
	 * @param endDate 終了日
	 * @return　ユーザーIDに関連する勤怠情報
	 */
	public List<Attendance> selectAttendanceByUserId(String userId, String startDate, String endDate) {
		List<Attendance> attendances = this.mapper.getAttendanceByUserId(userId, startDate, endDate);
		return attendances;
	}
	
	/**
	 * 勤務区分マスタ情報の取得処理
	 * @return 勤務区分マスタ情報
	 */
	public List<WorkingType> getAllWorkingType() {
		List<WorkingType> workingTypes = this.mapper.getAllWorkingType();
		return workingTypes;
	}
	
	
	/**
	 * 勤怠情報の登録処理
	 * @param userId ユーザーID
	 * @param attendanceYm　勤怠情報の年月
	 * @param status 勤怠情報のステータス
	 */
	public void upsertAttendance(String userId, String attendanceYm, Integer status) {
		this.mapper.upsertAttendance(userId, attendanceYm, status);
	}
	
	/**
	 * 勤怠詳細情報の登録処理
	 * @param userId ユーザーID
	 * @param attendanceList 勤怠詳細情報リスト
	 */
	public void upsertAttendanceDetail(String userId, List<Attendance> attendanceList) {
		this.mapper.upsertAttendanceDetail(userId, attendanceList);
	}
}
