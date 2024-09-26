package com.example.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
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
	private AttendanceService service;
	
	@Autowired
	private SmartValidator smartValidator;
	
	/**
	 * 勤怠管理画面を表示
	 * <p>当月の勤怠情報を表示する</p>
	 * @param form
	 * @param model
	 * @return 勤怠管理画面
	 */
	@GetMapping("")
	public String getAttendance(@ModelAttribute AttendanceForm form, Model model) {
		// ログインユーザーIDを取得
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(form.getAttendanceList() == null) {
			// 今日の日付から月初日と月末日を算出する
			LocalDate now = LocalDate.now();
			LocalDate firstDate = now.withDayOfMonth(1);
			LocalDate lastDate = now.withDayOfMonth(now.lengthOfMonth());
			
			// 日付をDBバインド用に変換
			String firstDateStr = firstDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String lastDateStr = lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
			// 勤怠情報を取得
			List<Attendance> attendanceList = this.service.selectAttendanceByUserId(userId, firstDateStr, lastDateStr);
			
			// 今日の日付をyyyyMM形式で変換
			String attendanceYm = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
			
			// データを画面にバインド
			form.setAttendanceList(attendanceList);
			form.setAttendanceYm(attendanceYm);
		}
		
		// 諸届マスタデータを取得
		List<WorkingType> workingTypeList = this.service.getAllWorkingType();
		model.addAttribute("workingTypeList", workingTypeList);
		
		// 画面を返却
		return "attendance/attendance";
	}
	
	/**
	 * 勤怠情報の保存処理
	 * @param model
	 * @param form
	 * @param br
	 * @return 勤怠管理画面
	 */
	@PostMapping("/save/rest")
	public String saveAttendance(Model model, @ModelAttribute AttendanceForm form, BindingResult br) {
		// 選択された明細行のみチェック対象とする
		for (int i = 0; i < form.getAttendanceList().size(); i++) {
			Attendance rec = form.getAttendanceList().get(i);
			if (rec.getCheckedFlg()) {
				br.pushNestedPath("attendanceList[" + i + "]");
				this.smartValidator.validate(rec, br);
				br.popNestedPath();
			}
		}
		// 入力チェック
		if(br.hasErrors()) {
			return getAttendance(form, model);
		}
		
		// ログインユーザーIDを取得
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		
		// 勤怠情報の年月
		String attendanceYm = form.getAttendanceYm();
		
		// DB登録用の勤怠情報リストを作成
		/* 画面に返すformとDB登録するformを分けておく */
		List<Attendance> attendanceList = form.getAttendanceList();
		List<Attendance> insertList = new ArrayList<Attendance>(attendanceList);
		
		// 勤怠情報の登録
		this.service.upsertAttendance(userId, attendanceYm, 0); // ヘッダー
		this.service.upsertAttendanceDetail(userId, insertList); // 明細
		
		// 諸届マスタデータを取得
		List<WorkingType> workingTypeList = this.service.getAllWorkingType();
		model.addAttribute("workingTypeList", workingTypeList);
		
		// 画面を表示
		return "attendance/attendance";
	}
}
