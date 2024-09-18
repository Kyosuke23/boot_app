package com.example.domain.attendance.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Attendance {

	private String userId;
	private String attendanceYm;
	private Integer status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date attendanceDate; 
	
	private Integer weekdayCd;
	private String weekdayName;
	private Integer workingTypeId;
	private String workingTypeName;
	private String startTime;
	private String endTime;
	private String restTime;
}
