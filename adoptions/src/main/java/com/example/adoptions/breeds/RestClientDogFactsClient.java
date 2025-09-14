package com.example.adoptions.breeds;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
class RestClientDogFactsClient implements DogFactsClient {

	private final String BASE_URI = "https://dogapi.dog/api/v1/";

	private final RestClient restClient;

	RestClientDogFactsClient(RestClient.Builder restClient) {
		this.restClient = restClient.baseUrl(BASE_URI).build();
	}

	@Override
	public BreedFacts getBreeds(int page) {
		return this.restClient//
			.get()//
			.uri("breeds")//
			.attribute("page[number]", page)//
			.retrieve()//
			.body(BreedFacts.class);
	}

}
