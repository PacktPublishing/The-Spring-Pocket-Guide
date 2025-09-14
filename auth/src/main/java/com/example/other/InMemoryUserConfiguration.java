package com.example.other;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Set;

@Configuration
class InMemoryUserConfiguration {

	@Bean
	InMemoryUserDetailsManager userDetailsService(PasswordEncoder pe) {
		// <1>
		var robPassword = pe.encode("pw");
		var rob = User.withUsername("rob").roles("ADMIN", "USER").password(robPassword).build();

		var joshPassword = pe.encode("pw");
		var josh = User.withUsername("josh").roles("USER").password(joshPassword).build();

		// <2>
		for (var pw : new String[] { joshPassword, robPassword }) {
			System.out.println("---------");
			System.out.println(pw);
		}

		var users = Set.of(rob, josh);

		// <3>
		return new InMemoryUserDetailsManager(users);
	}

}
