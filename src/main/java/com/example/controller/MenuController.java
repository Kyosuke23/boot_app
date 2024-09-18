package com.example.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.attendance.model.Attendance;
import com.example.domain.attendance.model.WorkingType;
import com.example.domain.attendance.service.AttendanceService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.AttendanceForm;
import com.example.form.UserListForm;

/**
 * 左サイドバーのリンクに関するコントローラークラス
 * @author Kyosuke
 *
 */
@Controller
public class MenuController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * アドミン権限専用画面を表示
	 * @return アドミン権限専用画面
	 */
	@GetMapping("/top")
	public String getTop() {
		return "top/top";
	}
	
	/**
	 * ユーザーリスト画面を表示
	 * @param form
	 * @param model
	 * @param pageable
	 * @return ユーザーリスト画面
	 */
	@GetMapping("/user/list")
	public String getUserList(@ModelAttribute UserListForm form, Model model, @PageableDefault(size=10) Pageable pageable) {
		MUser user = this.modelMapper.map(form, MUser.class);
		Page<MUser> userList = this.userService.getUsers(user, pageable);
		model.addAttribute("page", userList);
		return "user/list";
	}
	
	/**
	 * ユーザーリスト画面(REST)を表示
	 * @param form
	 * @param model
	 * @param pageable
	 * @return ユーザーリスト画面(REST)
	 */
	@GetMapping("/user/list/rest")
	public String getUserListRest(@ModelAttribute UserListForm form, Model model, @PageableDefault(size=10) Pageable pageable) {
		MUser user = this.modelMapper.map(form, MUser.class);
		Page<MUser> userList = this.userService.getUsers(user, pageable);
		model.addAttribute("page", userList);
		return "user_rest/list";
	}
	
	/**
	 * アドミン権限専用画面を表示
	 * @return アドミン権限専用画面
	 */
	@GetMapping("/admin")
	public String getAdmin() {
		return "admin/admin";
	}
	
	/**
	 * 勤怠管理画面を表示
	 * <p>当月の勤怠情報を表示する</p>
	 * @param model
	 * @return
	 */
	@GetMapping("/attendance")
	public String getAttendance(AttendanceForm form, Model model) {
		// ログインユーザーIDを取得
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		// 今日の日付から月初日と月末日を算出する
		LocalDate now = LocalDate.now();
		LocalDate firstDate = now.withDayOfMonth(1);
		LocalDate lastDate = now.withDayOfMonth(now.lengthOfMonth());
		// 日付をDBバインド用に変換
        String firstDateStr = firstDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String lastDateStr = lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		// 勤怠情報を取得
		List<Attendance> attendanceList = this.attendanceService.selectAttendanceByUserId(userId, firstDateStr, lastDateStr);
		// 今日の日付をyyyyMM形式で変換
		String attendanceYm = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
		// データを画面にバインド
		form.setAttendanceList(attendanceList);
		form.setAttendanceYm(attendanceYm);
		// 諸届マスタデータを取得
		List<WorkingType> workingTypeList = this.attendanceService.getAllWorkingType();
		model.addAttribute("workingTypeList", workingTypeList);
		// 画面を返却
		return "attendance/attendance";
	}
}
