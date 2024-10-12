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
	    
	    //
	    private int accountSeq;          // 이체번호
	    private Date updateDate;         // 변동일자
	    private String type;             // 거래 구분 (개설/입금/출금 등)
	    private String memo;             // 적요
	    private double deltaAmount;      // 변동 금액
	    private double balance;
	    
	    
	    
		public double getBalance() {
			return balance;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}
		public int getAccountSeq() {
			return accountSeq;
		}
		public void setAccountSeq(int accountSeq) {
			this.accountSeq = accountSeq;
		}
		public Date getUpdateDate() {
			return updateDate;
		}
		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
		public double getDeltaAmount() {
			return deltaAmount;
		}
		public void setDeltaAmount(double deltaAmount) {
			this.deltaAmount = deltaAmount;
		}
	}