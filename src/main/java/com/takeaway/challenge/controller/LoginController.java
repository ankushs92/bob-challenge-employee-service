package com.takeaway.challenge.controller;

import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.req.LoginReq;
import com.takeaway.challenge.service.AuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class LoginController {

    private final AuthenticationService authenticationService;
    private final String jwtSecret;

    LoginController(
            final AuthenticationService authenticationService,
            @Value("${jwt.secret}") final String jwtSecret )
    {
        this.authenticationService = authenticationService;
        this.jwtSecret = jwtSecret;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final LoginReq req) {
        var uaPasswordToken = authenticationService.authenticate(req);
        var jwtToken = buildJwtToken(uaPasswordToken);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtToken);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    private String buildJwtToken(final UsernamePasswordAuthenticationToken token) {
        var user = (User) token.getPrincipal();
        var roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setSubject(user.getUsername())
                .claim("rol", roles)
                .compact();
    }
}
