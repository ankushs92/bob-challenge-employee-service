package com.takeaway.challenge.service;

import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.req.LoginReq;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthenticationService {

    User authenticate(LoginReq loginReq);

    UsernamePasswordAuthenticationToken authenticate(String jwtToken);

}

