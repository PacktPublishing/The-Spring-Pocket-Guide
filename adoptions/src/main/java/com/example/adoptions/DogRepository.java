package com.example.adoptions;

import org.springframework.data.repository.ListCrudRepository;

// <1>
interface DogRepository extends ListCrudRepository<Dog, Integer> {

	// <2>
	Dog findByName(String name);

}
