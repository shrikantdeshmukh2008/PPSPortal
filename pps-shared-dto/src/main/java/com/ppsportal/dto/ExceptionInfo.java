package com.ppsportal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExceptionInfo {

	private String code;
	private String msg;
	private LocalDateTime date;
	private int status;
	private String path;



}
