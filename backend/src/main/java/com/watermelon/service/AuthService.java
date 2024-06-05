package com.watermelon.service;

import com.watermelon.dto.request.AuthRequest;
import com.watermelon.dto.request.IntrospectRequest;
import com.watermelon.dto.response.AuthResponse;
import com.watermelon.dto.response.IntrospectResponse;

public interface AuthService {
	IntrospectResponse introspect(IntrospectRequest request);
	AuthResponse login(AuthRequest request );
	void register(AuthRequest request );
}
