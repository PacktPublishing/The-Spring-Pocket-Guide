package com.example.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

// <1>
@RestController
class JsonController {

	// <2>
	@GetMapping("/http/hello")
	Greeting greet(@RequestParam Optional<String> name) {
		return new Greeting("Hello " + name.orElse("World") + ", @ " + LocalDateTime.now());
	}

}

record Greeting(String message) {
}
