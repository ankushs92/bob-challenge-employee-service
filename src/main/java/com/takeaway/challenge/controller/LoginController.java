package com.takeaway.challenge.controller;

import com.takeaway.challenge.req.LoginReq;
import com.takeaway.challenge.service.AuthenticationService;
import com.takeaway.challenge.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationService authenticationService;

    LoginController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody final LoginReq req) {
        var uaPasswordToken = authenticationService.authenticate(req);
        var jwtToken = JwtUtil.buildToken(uaPasswordToken);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtToken);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

}
