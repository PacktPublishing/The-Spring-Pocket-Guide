package com.example.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
class MvcController {

	@GetMapping("/mvc/hello")
	String hello(Model model, @RequestParam(required = false) Optional<String> name) {
		model.addAttribute("message", "Hello " + name.orElse("World") + ", @ " + LocalDateTime.now());
		return "hello";
	}

}
