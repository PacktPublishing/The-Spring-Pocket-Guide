package com.example.adoptions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@ResponseBody
@RequestMapping("/http/dogs")
class MvcHttpController {

	private final DogService dogService;

	MvcHttpController(DogService dogService) {
		this.dogService = dogService;
	}

	@GetMapping
	Collection<Dog> all() {
		return dogService.all();
	}

	@PostMapping("/{dogId}/adoptions")
	void adopt(@PathVariable int dogId, @RequestParam String owner) {
		dogService.adopt(dogId, owner);
	}

}
