package com.project.javabank.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.javabank.dto.UserDTO;
import com.project.javabank.mapper.UserMapper;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	

	private final UserMapper userMapper;

	 public CustomUserDetailsService(UserMapper mapper) {
			this.userMapper = mapper;
		}
		
		@Override
		public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
			UserDTO userDTO = new UserDTO();		
			userDTO = userMapper.findByUserId(userid);
			
			// 해당 ID가 DB에 없을 때 처리
//	        if (userDTO == null) {
//	            throw new UsernameNotFoundException("없는 회원입니다.");
//	        }

	        // 고정된 사용자 아이디 처리
//	        if ("user1".equals(insertedUserId)) { 
//	            return User.builder()
//	                    .username("user1")
//	                    .password(passwordEncoder.encode("1234")) // 고정된 패스워드
//	                    .roles("USER")
//	                    .build();
//	        }
	        
	        // 해당 ID가 DB에 있는 경우
	        return User.builder()
	                .username(userDTO.getUserId())
	                .password(userDTO.getUserPw()) // 비밀번호도 필요시 가져와서 처리
	                .roles(userDTO.getUserRoles()) // 권한 설정
	                .build();
	        
		}

	}
