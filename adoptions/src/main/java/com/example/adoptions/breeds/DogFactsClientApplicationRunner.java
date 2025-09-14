package com.example.adoptions.breeds;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class DogFactsClientApplicationRunner implements ApplicationRunner {

	private final Map<String, DogFactsClient> dogFactsClients;

	DogFactsClientApplicationRunner(Map<String, DogFactsClient> dogFactsClients) {
		this.dogFactsClients = dogFactsClients;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.dogFactsClients.forEach((beanName, client) -> {
			System.out.println("beanName: " + beanName);
			client.getBreeds(1).data().forEach(System.out::println);
		});
	}

}
