package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.req.LoginReq;
import com.takeaway.challenge.service.AuthenticationService;
import com.takeaway.challenge.util.Assert;
import com.takeaway.challenge.util.Strings;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDetailsService userDetailsService;

    AuthenticationServiceImpl(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticate(final LoginReq loginReq) {
        Assert.notNull(loginReq, "loginReq cannot be null");
        var email = loginReq.getEmail();
        var password = loginReq.getPassword();

        if(!Strings.hasText(email) || !Strings.hasText(password)) {
            throw new BadCredentialsException("Bad credentials provided");
        }

        var user = userDetailsService.loadUserByUsername(email);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var dbPassword = user.getPassword();
        var isAccountEnabled = user.isEnabled();
        var isAccountNonLocked = user.isAccountNonLocked();
        var isAccountNonExpired = user.isAccountNonExpired();
        var isCredentialsNonExpired = user.isCredentialsNonExpired();
        var bcryptPasswordEncoder = new BCryptPasswordEncoder();

        if(!bcryptPasswordEncoder.matches(password, dbPassword)){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!isAccountEnabled){
            throw new DisabledException("Account has been disabled");
        }

        if(!isAccountNonLocked){
            throw new LockedException("Account has been Locked");
        }

        if(!isAccountNonExpired){
            throw new AccountExpiredException("Account has expired");
        }

        if(!isCredentialsNonExpired){
            throw new CredentialsExpiredException("Credentials for this account have expired");
        }

        return new UsernamePasswordAuthenticationToken(user, email, user.getAuthorities());
    }
}
