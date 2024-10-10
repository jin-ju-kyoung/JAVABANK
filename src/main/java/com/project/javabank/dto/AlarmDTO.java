package com.project.javabank.dto;

import java.util.Date;

public class AlarmDTO {
	
	private int alarmNum;           // 알람번호 (PK)
    private String userId;          // 유저 아이디 (FK)
    private String alarmIsRead;     // 알람 열람 여부 (NULL 가능)
    private String alarmCate;       // 알람 카테고리 (NULL 가능)
    private String alarmCont;       // 알람 내용 (NULL 가능)
    private Date alarmRegDate;      // 알람 등록일시 (NULL 가능)
	public int getAlarmNum() {
		return alarmNum;
	}
	public void setAlarmNum(int alarmNum) {
		this.alarmNum = alarmNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAlarmIsRead() {
		return alarmIsRead;
	}
	public void setAlarmIsRead(String alarmIsRead) {
		this.alarmIsRead = alarmIsRead;
	}
	public String getAlarmCate() {
		return alarmCate;
	}
	public void setAlarmCate(String alarmCate) {
		this.alarmCate = alarmCate;
	}
	public String getAlarmCont() {
		return alarmCont;
	}
	public void setAlarmCont(String alarmCont) {
		this.alarmCont = alarmCont;
	}
	public Date getAlarmRegDate() {
		return alarmRegDate;
	}
	public void setAlarmRegDate(Date alarmRegDate) {
		this.alarmRegDate = alarmRegDate;
	}
    
    

}
