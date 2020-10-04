package com.takeaway.challenge.controller;

import com.takeaway.challenge.constants.JwtConstants;
import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.req.LoginReq;
import com.takeaway.challenge.service.AuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class LoginController {

    private final AuthenticationService authenticationService;

    LoginController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final LoginReq req) {
        var uaPasswordToken = authenticationService.authenticate(req);
        var jwtToken = buildJwtToken(uaPasswordToken);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtToken);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    private String buildJwtToken(final User user) {
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
