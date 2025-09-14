package com.example.adoptions;

import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
class ShelterBatchGraphQLController {

	@BatchMapping
	Map<Dog, Shelter> shelter(List<Dog> dogs) {
		var map = new HashMap<Dog, Shelter>();
		for (var d : dogs) {
			map.put(d, new Shelter(Math.random() > 0.5 ? "San Francisco" : "Paris"));
		}
		return map;
	}

}
