package com.example.basics;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class Runner implements ApplicationRunner {

	private final Map<String, CustomerService> customerServices;

	// <1>
	Runner(Map<String, CustomerService> customerServices) {
		this.customerServices = customerServices;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.customerServices //
			.forEach((beanName, service) -> //
			service.all().forEach(System.out::println));
	}

}
