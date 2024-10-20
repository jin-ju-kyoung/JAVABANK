package com.project.javabank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
		private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	    private final CustomAccessDeniedHandler customAccessDeniedHandler;

	    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                CustomAccessDeniedHandler customAccessDeniedHandler) {
				this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
				this.customAccessDeniedHandler = customAccessDeniedHandler;
}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http	
			
			.authorizeHttpRequests(request -> request
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
					.requestMatchers("/css/**", "/images/**", "/js/**").permitAll()
					.requestMatchers("/", "/login.do", "/join.do", "/logout.do", 
										"/joinSuccess.do", "checkId.ajax", "/sendEmail.ajax", 
										"/favicon.ico", "/confirmCode.ajax", "/error").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin(form -> form					
						.loginPage("/login.do")
						.loginProcessingUrl("/login.do")
						.usernameParameter("userid")
						.passwordParameter("pw")
						//.defaultSuccessUrl("/bankMain.do", true)
						.successHandler(new CustomAuthenticationSuccessHandler()) // 커스토마이징 핸들러 사용
						.failureUrl("/login.do?error=true")
						.permitAll()
			)
			.exceptionHandling(exceptionHandling -> exceptionHandling
	                .authenticationEntryPoint(customAuthenticationEntryPoint)  // 인증 실패 처리
	                .accessDeniedHandler(customAccessDeniedHandler)  // 권한 실패 처리
            )
			.logout(Customizer.withDefaults());


		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public AuthenticationSuccessHandler customLoginSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
	
    }

