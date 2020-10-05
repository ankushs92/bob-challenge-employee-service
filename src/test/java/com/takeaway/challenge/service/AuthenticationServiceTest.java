package com.takeaway.challenge.service;

import com.takeaway.challenge.ChallengeApplicationTests;
import com.takeaway.challenge.domain.auth.Role;
import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.repository.UserRepository;
import com.takeaway.challenge.req.LoginReq;
import com.takeaway.challenge.util.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationServiceTest extends ChallengeApplicationTests {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    private String jwt = "";

    @BeforeEach
    public void buildJwt() {
        jwt = JwtUtil.buildToken(user);
    }


    @Test
    public void testLogin_AllValidCredentials_ShouldReturnValidUserObject() {
        var email = "root";
        var pass = "root";
        var loginReq = new LoginReq(email, pass);

        var authenticatedUser = authenticationService.authenticate(loginReq);
        assertThat(user.getId(), equalTo(authenticatedUser.getId()));
    }

    @Test
    public void testLogin_InvalidUsername_ShouldThrowException() {
        var email = "Something Else";
        var pass = "root";
        var loginReq = new LoginReq(email, pass);

        assertThrows(TakeawayException.class, () -> authenticationService.authenticate(loginReq));
    }

    @Test
    public void testLogin_WrongPassword_ShouldThrowException() {
        var email = "root";
        var pass = "Something else";
        var loginReq = new LoginReq(email, pass);

        assertThrows(TakeawayException.class, () -> authenticationService.authenticate(loginReq));
    }

    @Test
    public void testLogin_EmptyUsernamePass_ShouldThrowException() {
        var email = "";
        var pass = "Something else";
        var loginReq = new LoginReq(email, pass);

        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(loginReq));
    }


    @Test
    public void testAuthorization_ValidJwtToken_ShouldReturnUsernamePasswordToken() {
        var usernamePassToken = authenticationService.authenticate(jwt);
        var principal = (User) usernamePassToken.getPrincipal();

        assertThat(user.getId(), equalTo(principal.getId()));
    }

    @Test
    public void testAuthorization_InValidJwtToken_ShouldReturnUsernamePasswordToken() {
        assertThrows(Exception.class, () -> authenticationService.authenticate("Invalid Jwt Token"));
    }

    @Test
    public void testAuthorization_ValidTokenButDisabledUser_ShouldThrowException() {
        user.setEnabled(false);
        userRepository.save(user);

        assertThrows(TakeawayException.class, () -> authenticationService.authenticate(jwt));
    }




}
