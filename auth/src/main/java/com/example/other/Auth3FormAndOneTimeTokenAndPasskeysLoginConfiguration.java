package com.example.other;

import com.example.auth.ConsoleMailOneTimeTokenGenerationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
class Auth3FormAndOneTimeTokenAndPasskeysLoginConfiguration {

	@Bean
	SecurityFilterChain authServerSecurityFilterChain(ConsoleMailOneTimeTokenGenerationSuccessHandler ott,
			HttpSecurity http) throws Exception {
		return http//
			.authorizeHttpRequests(ae -> ae.anyRequest().authenticated())//
			.formLogin(Customizer.withDefaults())//
			.oneTimeTokenLogin(o -> o.tokenGenerationSuccessHandler(ott))//
			.webAuthn(wa -> wa //
				.rpId("localhost") //
				.rpName("bootiful") //
				.allowedOrigins("http://localhost:9090", "http://127.0.0.1:9090") //
			)
			.build();
	}

}
