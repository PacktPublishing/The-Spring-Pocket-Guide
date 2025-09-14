package com.example.adoptions;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class DogModelAssembler implements RepresentationModelAssembler<Dog, EntityModel<Dog>> {

	@Override
	public EntityModel<Dog> toModel(Dog entity) {
		var controller = HateoasMvcController.class;
		var all = linkTo(controller).withRel("dogs");
		var adoptions = linkTo(methodOn(controller).adopt(entity.id(), null)).withRel("adoptions");
		var selfRel = linkTo(controller, entity.id()).withSelfRel();
		return EntityModel.of(entity, selfRel, all, adoptions);
	}

}
