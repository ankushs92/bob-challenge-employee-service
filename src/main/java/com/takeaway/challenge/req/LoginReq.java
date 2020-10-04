package com.takeaway.challenge.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginReq {

    private final String email;
    private final String password;

    public LoginReq(
            @JsonProperty("email") final String email,
            @JsonProperty("password") final String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
