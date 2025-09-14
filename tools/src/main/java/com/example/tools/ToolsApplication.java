package com.example.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class ToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolsApplication.class, args);
	}

	@Bean
	MethodToolCallbackProvider methodToolCallbackProvider(DogAdoptionSchedulingService service) {
		return MethodToolCallbackProvider.builder().toolObjects(service).build();
	}

}

@Component
class DogAdoptionSchedulingService {

	private final ObjectMapper om;

	DogAdoptionSchedulingService(ObjectMapper om) {
		this.om = om;
	}

	@Tool(description = "schedule an appointment to adopt a dog")
	String scheduleDogAdoptionAppointment(@ToolParam(description = "the id of the dog") int dogId,
			@ToolParam(description = "the name of the dog") String name) throws Exception {
		System.out.println("scheduleDogAdoptionAppointment [dogId=" + dogId + ", name=" + name + "]");
		return this.om.writeValueAsString(Instant.now().plus(3, ChronoUnit.DAYS));
	}

}
