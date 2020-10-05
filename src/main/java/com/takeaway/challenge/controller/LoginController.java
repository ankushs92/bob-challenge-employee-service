package com.takeaway.challenge.controller;

import com.takeaway.challenge.req.LoginReq;
import com.takeaway.challenge.service.AuthenticationService;
import com.takeaway.challenge.util.JwtUtil;
import io.swagger.annotations.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Login")
public class LoginController {

    private final AuthenticationService authenticationService;

    LoginController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ApiOperation(value = "Login to get JWT Token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in. You will receive the JWT Token in 'Authorization' header"),
            @ApiResponse(code = 403, message = "Bad Credentials. Make sure you provided the correct email and password and that the account is still active"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> login(
            @RequestBody
            @ApiParam(value = "Pass username and password as JSON")
            final LoginReq req
    )
    {
        var uaPasswordToken = authenticationService.authenticate(req);
        var jwtToken = JwtUtil.buildToken(uaPasswordToken);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtToken);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

}
