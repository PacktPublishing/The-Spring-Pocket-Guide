package com.example.adoptions;

import com.example.adoptions.breeds.DogFactsClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
class DogService {

	private final DogRepository dogRepository;

	private final DogFactsClient dogFactsClient;

	DogService(DogRepository dogRepository, DogFactsClient dogFactsClient) {
		this.dogRepository = dogRepository;
		this.dogFactsClient = dogFactsClient;
	}

	Dog byId(int id) {
		return dogRepository.findById(id).orElse(null);
	}

	void adopt(int dogId, String newOwner) {
		dogRepository.findById(dogId).ifPresent(dog -> {
			var updated = dogRepository.save(new Dog(dogId, dog.name(), newOwner, dog.description()));
			System.out.println("updated [" + updated + "]");
		});
	}

	Collection<Dog> all() {
		return dogRepository.findAll();
	}

}
