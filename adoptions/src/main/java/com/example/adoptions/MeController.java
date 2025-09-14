package com.example.adoptions;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
@ResponseBody
class MeController {

	@GetMapping("/me")
	Map<String, String> me(Principal principal) {
		if (principal instanceof JwtAuthenticationToken jwtAuthenticationToken) {
			var jwt = jwtAuthenticationToken.getToken();
			var tokenValue = jwt.getTokenValue();
			System.out.println("tokenValue: " + System.lineSeparator() + tokenValue);
		}
		return Map.of("name", principal.getName());
	}

}
