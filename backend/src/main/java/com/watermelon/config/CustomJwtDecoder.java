package com.watermelon.config;

import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.watermelon.dto.request.IntrospectRequest;
import com.watermelon.dto.response.IntrospectResponse;
import com.watermelon.service.AuthService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
	    @Value("${jwt.signerKey}")
	    private String signerKey;

	    private final AuthService authService;

	    private NimbusJwtDecoder nimbusJwtDecoder = null;

	    @Override
	    public Jwt decode(String token) throws JwtException {
	        try {
	            IntrospectResponse response = authService.introspect(
	            		IntrospectRequest.builder()
	                    .token(token)
	                    .build());

	            if (!response.isValid())
	                throw new JwtException("Token invalid");
	        } catch (ParseException e) {
	            throw new JwtException(e.getMessage());
	        }

	        if (Objects.isNull(nimbusJwtDecoder)) {
	            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
	            nimbusJwtDecoder = NimbusJwtDecoder
	                    .withSecretKey(secretKeySpec)
	                    .macAlgorithm(MacAlgorithm.HS512)
	                    .build();
	        }

	        return nimbusJwtDecoder.decode(token);
	    }
}
