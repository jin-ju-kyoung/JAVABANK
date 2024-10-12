package com.project.javabank.dto;

import java.util.Date;

public class ProductDTO {
	private String productAccount;   // 계좌번호 (PK)
    private int productPw;           // 계좌 비밀번호
    private String userId;           // 아이디 (FK)
    private String category;         // 상품 구분 (정기예금/정기적금)
    private Date autoTransferDate;   // 자동이체일
    private double monthlyPayment;   // 월납입금액
    private Date regDate;            // 계좌 개설일
    private Date expiryDate;         // 만기일자
    private double interestRate;     // 이자율
    private String depositAccount;   // 계좌번호(FK) 자동이체 통장

    // Getters and Setters
    public String getProductAccount() { return productAccount; }
    public void setProductAccount(String productAccount) { this.productAccount = productAccount; }

    public int getProductPw() { return productPw; }
    public void setProductPw(int productPw) { this.productPw = productPw; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getAutoTransferDate() { return autoTransferDate; }
    public void setAutoTransferDate(Date autoTransferDate) { this.autoTransferDate = autoTransferDate; }

    public double getMonthlyPayment() { return monthlyPayment; }
    public void setMonthlyPayment(double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    public Date getRegDate() { return regDate; }
    public void setRegDate(Date regDate) { this.regDate = regDate; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public String getDepositAccount() { return depositAccount; }
    public void setDepositAccount(String depositAccount) { this.depositAccount = depositAccount; }

    
    //
    private int productSeq;          // 이체 번호
    private Date updateDate;         // 변동일자
    private String type;             // 거래 구분
    private String memo;             // 적요
    private double deltaAmount;      // 변동 금액
    private double balance;          // 잔액

	public int getProductSeq() {
		return productSeq;
	}
	public void setProductSeq(int productSeq) {
		this.productSeq = productSeq;
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
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
    
       
    

}