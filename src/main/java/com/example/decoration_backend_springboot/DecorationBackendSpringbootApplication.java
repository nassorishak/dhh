package com.example.decoration_backend_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class  DecorationBackendSpringbootApplication {
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(DecorationBackendSpringbootApplication.class, args);
	}

}
