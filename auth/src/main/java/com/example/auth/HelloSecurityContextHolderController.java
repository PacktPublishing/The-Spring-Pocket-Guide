package com.example.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
class HelloSecurityContextHolderController {

	@GetMapping("/sch")
	Map<String, String> hello() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		return Map.of("name", authentication.getName());
	}

}
