package com.takeaway.challenge.service;

import com.takeaway.challenge.req.LoginReq;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthenticationService {

    UsernamePasswordAuthenticationToken authenticate(LoginReq loginReq);


}

