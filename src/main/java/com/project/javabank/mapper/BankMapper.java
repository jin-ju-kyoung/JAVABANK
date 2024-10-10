package com.project.javabank.mapper;

import java.util.Map;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankMapper {

	@Autowired
	private SqlSession sqlSession;
	
	public String generateAccountNumber() {
	    StringBuilder accountNumber = new StringBuilder();

	    // 16자리 숫자를 랜덤으로 생성 (4자리씩)
	    Random random = new Random();
	    for (int i = 0; i < 4; i++) {
	        int segment = random.nextInt(9000) + 1000;  // 1000~9999 사이의 4자리 숫자 생성
	        accountNumber.append(segment);
	        if (i < 3) {
	            accountNumber.append("-");  // 마지막 그룹 뒤에는 '-'를 붙이지 않음
	        }
	    }
	    //
	    System.out.println("계좌번호 : "+ random);

	    return accountNumber.toString();
	}
	
	public boolean checkIfUserHasAccount(String userId) {
		int accountCount = sqlSession.selectOne("getAccountCountByUserId",userId);
		//
		System.out.println("계좌갯수 : "+accountCount);
		return accountCount>0;
	}
	
	public int saveAccount(Map<String, Object> params) {
		//
		System.out.println(params);
		int res = sqlSession.insert("saveAccount", params);
		return res;
	}
	
}
