package com.example.basics;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Service
class DataSourceCustomerService implements CustomerService {

	private final DataSource dataSource;

	DataSourceCustomerService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Collection<Customer> all() {
		var all = new ArrayList<Customer>();
		try (var connection = dataSource.getConnection()) {
			try (var preparedStatement = connection.prepareStatement("select * from customer");) {
				var resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					all.add(new Customer(resultSet.getString("name"), resultSet.getInt("id")));
				}
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return all;
	}

}
