package com.example.domain.attendance.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Attendance {

	// チェックボックス
	private Boolean isChecked;
	// ユーザーID
	private String userId;
	// 勤怠年月
	private String attendanceYm;
	// ステータス
	private Integer status;
	// 勤怠年月日
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date attendanceDate; 
	// 曜日コード
	private Integer weekdayCd;
	// 曜日名称
	private String weekdayName;
	// 勤務区分
	private Integer workingTypeId;
	// 勤務区分名称
	private String workingTypeName;
	// 開始時刻
	@NotEmpty
	private String startTime;
	// 終了時刻
	@NotEmpty
	private String endTime;
	@NotEmpty
	// 休憩時間
	private String restTime;
}
