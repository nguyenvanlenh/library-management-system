package com.watermelon.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.watermelon.dto.request.AuthRequest;
import com.watermelon.dto.request.IntrospectRequest;
import com.watermelon.dto.response.ApiResponse;
import com.watermelon.dto.response.AuthResponse;
import com.watermelon.dto.response.IntrospectResponse;
import com.watermelon.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping(value ={"/register","/signup"})
	public ApiResponse<AuthResponse> registerUser(@RequestBody AuthRequest request) {
		authService.register(request);
		return ApiResponse.<AuthResponse>builder()
			.status(201)
			.message("Registration successful")
			.build();
	}

	@PostMapping(value = {"/login", "/signin"})
	public ApiResponse<AuthResponse> loginUser(@RequestBody AuthRequest request) {
		AuthResponse result = authService.login(request);
		return ApiResponse.<AuthResponse>builder()
			.status(200)
			.message("Authenticated successfully")
			.data(result)
			.build();
	}

	@PostMapping("/introspect")
	public ApiResponse<IntrospectResponse> introspectToken(@RequestBody IntrospectRequest request)
			throws ParseException, JOSEException {
		var result = authService.introspect(request);
		return ApiResponse.<IntrospectResponse>builder()
			.status(200)
			.message("Token is valid")
			.data(result)
			.build();
	}
}
