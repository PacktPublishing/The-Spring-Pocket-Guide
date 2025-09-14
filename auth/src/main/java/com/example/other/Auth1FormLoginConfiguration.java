package com.example.other;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class Auth1FormLoginConfiguration {

	@Bean
	SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
		return http//
			.authorizeHttpRequests(ae -> ae.anyRequest().authenticated())//
			.formLogin(Customizer.withDefaults())//
			.build();
	}

}
