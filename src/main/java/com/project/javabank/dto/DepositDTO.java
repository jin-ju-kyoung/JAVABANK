package com.project.javabank.dto;

import java.util.Date;

public class DepositDTO {
	 private String depositAccount;   // 계좌번호 (PK)
	    private int depositPw;           // 계좌 비밀번호
	    private String userId;           // 아이디 (FK)
	    private String category;         // 계좌 구분 (입출금통장)
	    private Date regDate;            // 계좌 개설일
	    private double interestRate;     // 이자율
	    private double transactionLimit; // 1일 이체한도
	    private String mainAccount;      // 주거래 계좌 여부

	    // Getters and Setters
	    public String getDepositAccount() { return depositAccount; }
	    public void setDepositAccount(String depositAccount) { this.depositAccount = depositAccount; }

	    public int getDepositPw() { return depositPw; }
	    public void setDepositPw(int depositPw) { this.depositPw = depositPw; }

	    public String getUserId() { return userId; }
	    public void setUserId(String userId) { this.userId = userId; }

	    public String getCategory() { return category; }
	    public void setCategory(String category) { this.category = category; }

	    public Date getRegDate() { return regDate; }
	    public void setRegDate(Date regDate) { this.regDate = regDate; }

	    public double getInterestRate() { return interestRate; }
	    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

	    public double getTransactionLimit() { return transactionLimit; }
	    public void setTransactionLimit(double transactionLimit) { this.transactionLimit = transactionLimit; }

	    public String getMainAccount() { return mainAccount; }
	    public void setMainAccount(String mainAccount) { this.mainAccount = mainAccount; }
	}