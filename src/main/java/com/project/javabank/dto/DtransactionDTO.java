package com.project.javabank.dto;

import java.util.Date;

public class DtransactionDTO { //입출금계좌내역
	private int accountSeq;          // 이체번호 (PK)
    private String depositAccount;   // 계좌번호 (FK)
    private Date updateDate;         // 변동일자
    private String type;             // 구분 (개설/입금/출금/이자입금)
    private String memo;             // 적요
    private double deltaAmount;      // 변동금액
    private double balance;          // 잔액

    // Getters and Setters
    public int getAccountSeq() { return accountSeq; }
    public void setAccountSeq(int accountSeq) { this.accountSeq = accountSeq; }

    public String getDepositAccount() { return depositAccount; }
    public void setDepositAccount(String depositAccount) { this.depositAccount = depositAccount; }

    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }

    public double getDeltaAmount() { return deltaAmount; }
    public void setDeltaAmount(double deltaAmount) { this.deltaAmount = deltaAmount; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}