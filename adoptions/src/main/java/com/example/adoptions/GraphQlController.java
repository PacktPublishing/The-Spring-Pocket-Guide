package com.example.adoptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
class GraphQlController {

	private final Logger log = LoggerFactory.getLogger(GraphQlController.class);

	private final DogService dogService;

	GraphQlController(DogService dogService) {
		this.dogService = dogService;
	}

	// <1>
	@QueryMapping
	Collection<Dog> dogs() {
		return dogService.all();
	}

	// <2>
	@MutationMapping
	boolean adopt(@Argument int id, @Argument String owner) {
		try {
			dogService.adopt(id, owner);
			return true;
		} //
		catch (IllegalArgumentException e) {
			this.log.error("Error while adopting dog with id: " + id, e);
		}
		return false;
	}

	@SchemaMapping
	Shelter shelter(Dog dog) {
		return new Shelter(Math.random() > 0.5 ? "San Francisco" : "Paris");
	}

}
