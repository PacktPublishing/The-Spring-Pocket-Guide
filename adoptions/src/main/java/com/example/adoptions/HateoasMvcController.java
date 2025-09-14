package com.example.adoptions;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.*;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
@RestController
@RequestMapping("/hateoas/dogs")
class HateoasMvcController {

	private final DogService dogService;

	private final DogModelAssembler dogAssembler;

	HateoasMvcController(DogService dogService, DogModelAssembler dogAssembler) {
		this.dogService = dogService;
		this.dogAssembler = dogAssembler;
	}

	@GetMapping
	CollectionModel<EntityModel<Dog>> all() {
		return dogAssembler.toCollectionModel(this.dogService.all());
	}

	@PostMapping("/{dogId}/adoptions")
	EntityModel<Dog> adopt(@PathVariable int dogId, @RequestParam String owner) {
		this.dogService.adopt(dogId, owner);
		var dog = this.dogService.byId(dogId);
		return dogAssembler.toModel(dog);
	}

}
