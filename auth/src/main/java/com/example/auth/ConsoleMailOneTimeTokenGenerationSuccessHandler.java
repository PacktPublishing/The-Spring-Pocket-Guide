package com.example.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleMailOneTimeTokenGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken)
			throws IOException, ServletException {

		System.out
			.println("go to http://localhost:9090/login/ott?token=" + oneTimeToken.getTokenValue() + " to login.");

		response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
		response.getWriter().write("you've got console mail!");

	}

}
