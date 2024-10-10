package com.project.javabank.dto;

import java.util.Date;

public class ProductDTO {
	private String productAccount;  // 계좌번호 (PK)
    private int productPw;          // 계좌비밀번호
    private String userId;          // 유저 아이디 (FK)
    private String category;        // 상품구분 (정기예금/정기적금)
    private String productType;     // 구분 (개설/입금/만기출금/중도해지)
    private Date updateDate;        // 변동일자 (NULL 가능)
    private String productMemo;     // 적요 (NULL 가능)
    private Double deltaAmount;     // 변동금액 (NULL 가능)
    private Double accountBalance;  // 잔액
    private Date autoTransferDate;  // 자동이체일
    private Double monthlyPayment;  // 월납입금액
    private Date accountRegDate;    // 계좌개설일
    private Date expiryDate;        // 만기일자
    private Double interestRate;    // 이자율
    private Double interestAmount;  // 이자액 (NULL 가능)
    private String depositAccount;  // 입출금 계좌번호 (FK)
	public String getProductAccount() {
		return productAccount;
	}
	public void setProductAccount(String productAccount) {
		this.productAccount = productAccount;
	}
	public int getProductPw() {
		return productPw;
	}
	public void setProductPw(int productPw) {
		this.productPw = productPw;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getProductMemo() {
		return productMemo;
	}
	public void setProductMemo(String productMemo) {
		this.productMemo = productMemo;
	}
	public Double getDeltaAmount() {
		return deltaAmount;
	}
	public void setDeltaAmount(Double deltaAmount) {
		this.deltaAmount = deltaAmount;
	}
	public Double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Date getAutoTransferDate() {
		return autoTransferDate;
	}
	public void setAutoTransferDate(Date autoTransferDate) {
		this.autoTransferDate = autoTransferDate;
	}
	public Double getMonthlyPayment() {
		return monthlyPayment;
	}
	public void setMonthlyPayment(Double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	public Date getAccountRegDate() {
		return accountRegDate;
	}
	public void setAccountRegDate(Date accountRegDate) {
		this.accountRegDate = accountRegDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public Double getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}
	public String getDepositAccount() {
		return depositAccount;
	}
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
    
    

}
