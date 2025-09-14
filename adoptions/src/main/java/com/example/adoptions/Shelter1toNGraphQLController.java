package com.example.adoptions;

import org.springframework.context.annotation.Profile;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Profile("inefficient")
@Controller
class Shelter1toNGraphQLController {

	@SchemaMapping
	Shelter shelter(Dog dog) {
		System.out.println("looking for the shelter for " + dog.id() + '/' + dog.name());
		return new Shelter(Math.random() > 0.5 ? "San Francisco" : "Paris");
	}

}
