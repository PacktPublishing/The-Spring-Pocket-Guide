package com.example.other;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class ProductionDataSourceConfiguration1 {

	@Bean
	HikariDataSource dataSource() {
		var builder = DataSourceBuilder//
			.create(getClass().getClassLoader())//
			.type((Class<? extends DataSource>) HikariDataSource.class) //
			.driverClassName("org.postgresql.Driver");
		return (HikariDataSource) builder.url("jdbc:postgresql://localhost/mydatabase") //
			.username("myuser") //
			.password("secret") //
			.build();

	}

}
