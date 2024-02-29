package com.myblog.blog1;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;

@SpringBootApplication
public class Blog1Application {

	public static void main(String[] args) {SpringApplication.run(Blog1Application.class, args);
	}

//	@Bean
//	public AuthenticationManager authenticationManager(){
//		return new AuthenticationManager() {
//			@Override
//			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//				return null;
//			}
//		};
//	}
	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

}
