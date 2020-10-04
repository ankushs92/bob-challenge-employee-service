package com.takeaway.challenge.auth;

import com.takeaway.challenge.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final AuthenticationService authenticationService;

    public JwtAuthorizationFilter(final AuthenticationManager authenticationManager,final AuthenticationService authenticationService) {
        super(authenticationManager);
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException
    {
        var uri = request.getRequestURI();
        if (!uri.equals("/login")) {
            var jwtToken = request.getHeader("Authorization");
            var usernamePassToken = authenticationService.authenticate(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(usernamePassToken);
        }
        filterChain.doFilter(request, response);
    }
}
