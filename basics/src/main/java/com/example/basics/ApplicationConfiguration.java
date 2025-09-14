package com.example.basics;

import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;

@Configuration
class ApplicationConfiguration {

	// <1>
	@Bean
	EmbeddedDatabase dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	// <2>
	@Bean
	JdbcClient jdbcClient(DataSource dataSource) {
		return JdbcClient.create(dataSource);
	}

	// <3>
	@Bean
	SqlDataSourceScriptDatabaseInitializer sqlDataSourceScriptDatabaseInitializer(DataSource ds) {
		var settings = new DatabaseInitializationSettings();
		settings.setSchemaLocations(List.of("schema.sql"));
		settings.setDataLocations(List.of("data.sql"));
		return new SqlDataSourceScriptDatabaseInitializer(ds, settings);
	}

}
