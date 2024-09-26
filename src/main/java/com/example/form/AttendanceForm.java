package com.example.form;

import java.util.List;

import com.example.domain.attendance.model.Attendance;

import lombok.Data;

@Data
public class AttendanceForm {

	// 勤怠年月
	private String attendanceYm;
	// 勤怠詳細情報
	List<Attendance> attendanceList;
}
