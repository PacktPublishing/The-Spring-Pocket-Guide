package com.example.adoptions.breeds;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class DogFactsClientConfiguration {

	// <1>
	@Bean
	HttpServiceProxyFactory httpServiceProxyFactory(RestClient.Builder builder) {
		return HttpServiceProxyFactory //
			.builder() //
			.exchangeAdapter(RestClientAdapter.create(builder.build())) //
			.build();
	}

	// <2>
	@Bean
	@Primary
	DogFactsClient dogFactsClient(HttpServiceProxyFactory httpServiceProxyFactory) {
		// <3>
		return httpServiceProxyFactory.createClient(DogFactsClient.class);
	}

}
