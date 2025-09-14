package com.example.other;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@ConfigurationProperties("datasource")
record DataSourceProperties(String url) {
}

// <1>
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
class ProductionDataSourceConfiguration2 {

	@Bean
	HikariDataSource dataSource(//
			Environment environment, //
			@Value("${datasource.password}") String pw, //
			DataSourceProperties properties//
	) {
		var username = environment.getProperty("datasource.username");
		var password = pw; // <2>
		var url = properties.url();
		var builder = DataSourceBuilder//
			.create(getClass().getClassLoader())//
			.type((Class<? extends DataSource>) HikariDataSource.class) //
			.driverClassName("org.postgresql.Driver");
		return (HikariDataSource) builder.url(url) //
			.username(username) //
			.password(password) //
			.build();

	}

}
