package com.example.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
class UserPasswordMigrationConfiguration {

	@Bean
	UserDetailsPasswordService userDetailsPasswordService(JdbcUserDetailsManager userDetailsManager) {
		return (user, newPassword) -> {
			var updated = User.withUserDetails(user).password(newPassword).build();
			userDetailsManager.updateUser(updated);
			return userDetailsManager.loadUserByUsername(updated.getUsername());
		};
	}

}
