package com.project.javabank.service;

import com.project.javabank.dto.UserDTO;
import com.project.javabank.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class KakaoAuthService {

    @Autowired
    private UserMapper userMapper;  // MyBatis 매퍼를 주입받음

    private final String CLIENT_ID = "YOUR_KAKAO_CLIENT_ID";  // 카카오 개발자 콘솔에서 발급받은 REST API 키
    private final String REDIRECT_URI = "YOUR_REDIRECT_URI";  // 카카오 개발자 콘솔에 설정한 리다이렉트 URI

    // Access Token 발급
    public String getAccessToken(String code) {
        // 1. Access Token을 얻기 위한 카카오 API 요청 URL
        String url = "https://kauth.kakao.com/oauth/token";
        
        // 2. 요청 헤더 설정 (Content-Type을 application/x-www-form-urlencoded로 설정)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // 3. 요청 바디 설정 (카카오 API에 필요한 파라미터 설정)
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);  // 클라이언트에서 받은 인가 코드

        // 4. 요청을 보낼 엔티티 생성 (헤더와 바디를 함께)
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        
        // 5. RestTemplate을 사용해 POST 요청을 보냄
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // 6. 응답 결과에서 Access Token을 파싱해 반환 (간단하게 설명, 실제로는 JSON을 파싱해야 함)
        String accessToken = parseAccessToken(response.getBody());
        
        return accessToken;
    }

    // 사용자 정보 가져오기
    public UserDTO getUserInfo(String accessToken) {
        // 1. 사용자 정보를 얻기 위한 카카오 API 요청 URL
        String url = "https://kapi.kakao.com/v2/user/me";
        
        // 2. 요청 헤더 설정 (Authorization에 Bearer 타입으로 Access Token 추가)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // 3. 요청을 보낼 엔티티 생성 (헤더만 필요)
        HttpEntity<String> request = new HttpEntity<>(headers);

        // 4. RestTemplate을 사용해 GET 요청을 보냄
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        // 5. 응답 결과에서 사용자 정보를 파싱해 UserDTO로 변환 (JSON 파싱 필요)
        UserDTO userInfo = parseUserInfo(response.getBody());

        return userInfo;
    }

    // 사용자 정보 저장
    public void saveUser(UserDTO userInfo) {
        // 1. MyBatis 매퍼를 사용해 사용자 정보를 DB에 저장
 //       userMapper.insertUser(userInfo);
    }

    // Access Token을 응답 바디에서 파싱하는 메서드 (간단한 예)
    private String parseAccessToken(String responseBody) {
        // JSON 파싱 로직 필요 (예를 들어, Jackson 또는 Gson 사용 가능)
        // 여기서는 간단히 Access Token을 추출했다고 가정
        // 실제로는 JSON 형태에서 "access_token" 키의 값을 가져와야 함
        return "parsed_access_token";
    }

    // 사용자 정보를 응답 바디에서 파싱하는 메서드 (간단한 예)
    private UserDTO parseUserInfo(String responseBody) {
        // JSON 파싱 로직 필요
        // JSON에서 "id", "nickname", "email" 등의 값을 추출하여 UserDTO에 설정
        UserDTO userInfo = new UserDTO();
//        userInfo.setId("parsed_id");
//        userInfo.setName("parsed_nickname");
//        userInfo.setEmail("parsed_email");

        return userInfo;
    }
}
