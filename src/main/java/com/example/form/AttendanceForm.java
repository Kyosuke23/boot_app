package com.example.form;

import java.util.List;

import com.example.domain.attendance.model.Attendance;

import lombok.Data;

@Data
public class AttendanceForm {

	private String attendanceYm;
	List<Attendance> attendanceList;
}
