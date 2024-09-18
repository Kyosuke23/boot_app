package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.attendance.model.Attendance;
import com.example.domain.attendance.model.WorkingType;
import com.example.domain.attendance.service.AttendanceService;
import com.example.form.AttendanceForm;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;

	@PostMapping("/save/rest")
	public String saveAttendance(@ModelAttribute AttendanceForm form, Model model) {
		// ログインユーザーIDを取得
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		// 勤怠情報の年月
		String attendanceYm = form.getAttendanceYm();
		// 勤怠情報リスト
		List<Attendance> attendanceList = form.getAttendanceList();
		List<Attendance> insertList = new ArrayList<Attendance>(attendanceList);
		insertList.removeIf(item -> StringUtils.isEmpty(item.getStartTime()) && StringUtils.isEmpty(item.getEndTime()));
		// 勤怠情報の登録
		this.attendanceService.upsertAttendance(userId, attendanceYm, 0);      // ヘッダー
		this.attendanceService.upsertAttendanceDetail(userId, insertList); // 詳細
		// 諸届マスタデータを取得
		List<WorkingType> workingTypeList = this.attendanceService.getAllWorkingType();
		model.addAttribute("workingTypeList", workingTypeList);
		return "attendance/attendance";
	}
}
