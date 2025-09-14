package com.example.basics;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class JdbcClientCustomerService implements CustomerService {

	private final JdbcClient db;

	JdbcClientCustomerService(JdbcClient db) {
		this.db = db;
	}

	@Override
	public Collection<Customer> all() {
		return this.db.sql("select * from customer")
			.query((rs, rowNum) -> new Customer(rs.getString("name"), rs.getInt("id")))
			.list();
	}

}
