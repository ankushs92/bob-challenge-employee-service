package com.takeaway.challenge.auth;

import com.takeaway.challenge.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;

    CustomAuthenticationProvider(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return false;
    }
}
