package com.example.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

@Controller
@ResponseBody
class VirtualThreadsDemoController {

	private final RestClient http;

	VirtualThreadsDemoController(RestClient.Builder builder) {
		this.http = builder.build();
	}

	// <1>
	@GetMapping("/delay")
	String delay() {
		var log = Thread.currentThread() + ":::";
		var message = this.http.get().uri("http://localhost/delay/5").retrieve().body(String.class);
		log += Thread.currentThread();
		System.out.println(log);
		return message;
	}

}
