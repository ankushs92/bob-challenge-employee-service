package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.constants.JwtConstants;
import com.takeaway.challenge.domain.auth.User;
import com.takeaway.challenge.exception.TakeawayError;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.req.LoginReq;
import com.takeaway.challenge.service.AuthenticationService;
import com.takeaway.challenge.service.UserService;
import com.takeaway.challenge.util.Assert;
import com.takeaway.challenge.util.Strings;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserService userService;

    AuthenticationServiceImpl(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public User authenticate(final LoginReq loginReq) {
        Assert.notNull(loginReq, "loginReq cannot be null");
        var email = loginReq.getEmail();
        var password = loginReq.getPassword();

        if(!Strings.hasText(email) || !Strings.hasText(password)) {
            throw new BadCredentialsException("Bad credentials provided");
        }

        return getUser(email, password);
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticate(final String jwtToken) {
        Assert.notEmptyString(jwtToken, "jwtToken token cannot be null or empty");
        try {
            var parsedToken = Jwts
                    .parser()
                    .setSigningKey(JwtConstants.SECRET)
                    .parseClaimsJws(jwtToken.replace("Bearer ", ""));

            var username = parsedToken
                    .getBody()
                    .getSubject();

            if(!Strings.hasText(username)) {
                throw new UsernameNotFoundException("Username not found");
            }

            var user = getUser(username, null);

            var authorities = ((List<?>) parsedToken.getBody()
                    .get("rol")).stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(user, username, authorities);

        }
        catch (final Exception ex) {
            logger.error("", ex);
            throw new TakeawayException(TakeawayError.AUTH_01, HttpStatus.FORBIDDEN);
        }
    }

    private User getUser(final String email, final String password) {
        var user = userService.loadUserByUsername(email)
                              .orElseThrow(() ->new UsernameNotFoundException("User not found"));

        var dbPassword = user.getPassword();
        var isAccountEnabled = user.isEnabled();
        var isAccountNonLocked = user.isAccountNonLocked();
        var isAccountNonExpired = user.isAccountNonExpired();
        var isCredentialsNonExpired = user.isCredentialsNonExpired();
        var bcryptPasswordEncoder = new BCryptPasswordEncoder();

        if(Objects.nonNull(password)) {
            if(!bcryptPasswordEncoder.matches(password, dbPassword)) {
                throw new BadCredentialsException("Invalid username or password");
            }
        }

        if(!isAccountEnabled) {
            throw new DisabledException("Account has been disabled");
        }

        if(!isAccountNonLocked) {
            throw new LockedException("Account has been Locked");
        }

        if(!isAccountNonExpired) {
            throw new AccountExpiredException("Account has expired");
        }

        if(!isCredentialsNonExpired) {
            throw new CredentialsExpiredException("Credentials for this account have expired");
        }

        return user;
    }

}
