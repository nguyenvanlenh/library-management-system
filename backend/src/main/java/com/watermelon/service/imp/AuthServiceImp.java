package com.watermelon.service.imp;

import java.util.Collections;
import java.util.HashSet;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.watermelon.config.JwtTokenUtils;
import com.watermelon.dto.request.AuthRequest;
import com.watermelon.dto.request.IntrospectRequest;
import com.watermelon.dto.response.AuthResponse;
import com.watermelon.dto.response.IntrospectResponse;
import com.watermelon.entity.Role;
import com.watermelon.entity.User;
import com.watermelon.enums.ERole;
import com.watermelon.exception.ResourceNotFoundException;
import com.watermelon.repository.RoleRepository;
import com.watermelon.repository.UserRepository;
import com.watermelon.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JwtTokenUtils jwtTokenUtils;

	@Override
	public IntrospectResponse introspect(IntrospectRequest request) {
		String token = request.getToken();
		boolean isValid = true;
		try {
			jwtTokenUtils.verifyToken(token);
		} catch (Exception e) {
			isValid = false;
		}
		return IntrospectResponse.builder().valid(isValid).build();
	}

	@Override
	public AuthResponse login(AuthRequest request) {
		User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
			log.error("User not found for username: {}", request.getUsername());
			return new ResourceNotFoundException("user_not_found");
		});

		boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

		if (!authenticated) {
			log.warn("Authentication failed for username: {}", request.getUsername());
			throw new BadCredentialsException("login_bad_credentials");
		}

		String token = jwtTokenUtils.generateToken(user);
		log.info("Login successful for username: {}", request.getUsername());
		return AuthResponse.builder().token(token).authenticated(true).build();
	}

	@Override
	public void register(AuthRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			log.warn("Registration failed: username already exists: {}", request.getUsername());
			throw new ResourceNotFoundException("register_user_exists", request.getUsername());
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			log.warn("Registration failed: email already exists: {}", request.getEmail());
			throw new ResourceNotFoundException("register_email_exists", request.getEmail());
		}
		Role role = roleRepository.findById(ERole.STUDENT)
				.orElseThrow(() -> new ResourceNotFoundException("role_not_found"));
		User user = User.builder().username(request.getUsername()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.listRoles(new HashSet<>(Collections.singleton(role))).build();
		userRepository.save(user);

	}

}
