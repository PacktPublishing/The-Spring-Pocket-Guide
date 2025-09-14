package com.example.adoptions.breeds;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Collection;

public interface DogFactsClient {

	// <1>
	@GetExchange("https://dogapi.dog/api/v2/breeds")
	BreedFacts getBreeds(@RequestParam(value = "page[number]") int page);

	record BreedFacts(Collection<Breed> data) {
	}

	record Breed(String id, BreedAttributes attributes) {
	}

	record BreedAttributes(String name, String description, BreedLife life) {
	}

	record BreedLife(int min, int max) {
	}

}
