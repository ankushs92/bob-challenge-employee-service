package com.takeaway.challenge.util;

import com.takeaway.challenge.constants.JwtConstants;
import com.takeaway.challenge.domain.auth.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

public class JwtUtil {

    public static String buildToken(final User user) {
        var roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .signWith(JwtConstants.SECRET, SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setSubject(user.getUsername())
                .claim("rol", roles)
                .compact();

    }
}
