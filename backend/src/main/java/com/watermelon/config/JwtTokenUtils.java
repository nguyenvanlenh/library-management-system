package com.watermelon.config;


import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.watermelon.entity.User;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Component
public class JwtTokenUtils {
	@Value("${jwt.signerKey}")
	private String signerKey;
	
	public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("watermelon.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

	public SignedJWT verifyToken(String token) throws JOSEException ,ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
        	throw new RuntimeException("Unauthenticated");

        return signedJWT;
    }
    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");

         if (!CollectionUtils.isEmpty(user.getListRoles()))
            user.getListRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getId().toString());
                if (!CollectionUtils.isEmpty(role.getListPermissions()))
                    role.getListPermissions()
                            .forEach(permission -> stringJoiner.add(permission.getId().toString()));
            });

        return stringJoiner.toString();
    }

}
