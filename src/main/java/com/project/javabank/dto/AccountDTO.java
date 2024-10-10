package com.project.javabank.dto;

import java.util.Date;

public class AccountDTO {
	private String depositAccount;  // 계좌번호 (PK)
    private int depositPw;          // 계좌비밀번호
    private String userId;          // 유저 아이디 (FK)
    private String category;        // 상품구분 (입출금통장)
    private Date updateDate;        // 변동일자 (NULL 가능)
    private String accountType;     // 구분 (개설/입금/출금)
    private String accountMemo;     // 적요 (NULL 가능)
    private Double deltaAmount;     // 변동금액 (NULL 가능)
    private Double accountBalance;  // 잔액
    private Date accountRegDate;    // 계좌개설일
    private Double interestRate;    // 이자율
    private Double interestAmount;  // 이자액 (NULL 가능)
    private Double accountLimit;  // 이체한도
    private String mainAccount;     // 주거래계좌 여부
	public String getDepositAccount() {
		return depositAccount;
	}
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	public int getDepositPw() {
		return depositPw;
	}
	public void setDepositPw(int depositPw) {
		this.depositPw = depositPw;
	}
	public String getUserId() {
		return userId;
	}
	public Double getAccountLimit() {
		return accountLimit;
	}
	public void setAccountLimit(Double accountLimit) {
		this.accountLimit = accountLimit;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountMemo() {
		return accountMemo;
	}
	public void setAccountMemo(String accountMemo) {
		this.accountMemo = accountMemo;
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
	public Date getAccountRegDate() {
		return accountRegDate;
	}
	public void setAccountRegDate(Date accountRegDate) {
		this.accountRegDate = accountRegDate;
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
	public String getMainAccount() {
		return mainAccount;
	}
	public void setMainAccount(String mainAccount) {
		this.mainAccount = mainAccount;
	}
    
    

}
