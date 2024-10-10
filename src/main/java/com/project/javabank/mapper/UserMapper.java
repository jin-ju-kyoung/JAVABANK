package com.project.javabank.mapper;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.javabank.dto.UserDTO;

@Service
public class UserMapper {

	@Autowired
	private SqlSession sqlSession;
	
	public int joinSuccess(Map<String, Object> params) {
	    // BCryptPasswordEncoder 인스턴스를 생성
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    
	 // params에서 비밀번호를 가져와 암호화한 후, 다시 덮어씌움
	    String rawPassword = (String) params.get("userPw");
	    if (rawPassword != null && !rawPassword.isEmpty()) {
	        String encodedPassword = passwordEncoder.encode(rawPassword);
	        params.put("userPw", encodedPassword);
	    } else {
	        throw new IllegalArgumentException("비밀번호가 입력되지 않았습니다.");
	    }
	    
	    // MyBatis를 사용하여 데이터베이스에 삽입
	    int res = sqlSession.insert("joinSuccess", params);
	    
	    
	    // 결과값 반환
	    return res;
	    }
	
	public UserDTO findByUserId(String userid){
		System.out.println(userid);
		return sqlSession.selectOne("findByUserId", userid);
	}
	
	
	
	
}
