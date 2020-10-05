package com.takeaway.challenge.security;

import com.takeaway.challenge.ChallengeApplicationTests;
import com.takeaway.challenge.req.LoginReq;
import com.takeaway.challenge.util.Json;
import com.takeaway.challenge.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationTest extends ChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin_shouldReturnHttpStatusOkWithJwtTokenInAuthHeader() throws Exception {
        var jwtToken = JwtUtil.buildToken(user);
        var json = Json.encode(new LoginReq(EMAIL, PASSWORD));
        var loginReq = MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json);

        mockMvc.perform(loginReq)
               .andExpect(status().isOk())
               .andExpect(header().string("Authorization", "Bearer " + jwtToken));
    }



    @Test
    public void testLogin_RightEmailButWrongPassword_shouldReturnHttp403Forbidden() throws Exception {
        var json = Json.encode(new LoginReq(EMAIL, "Wrong Password"));
        var loginReq = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        mockMvc.perform(loginReq)
                .andExpect(status().isForbidden());

    }

    @Test
    public void testLogin_RightEmailAndPassButDisabledUser_shouldReturnHttp403Forbidden() throws Exception {
        user.setEnabled(false);
        userRepository.save(user);

        var json = Json.encode(new LoginReq(EMAIL, "Wrong Password"));
        var loginReq = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        mockMvc.perform(loginReq)
                .andExpect(status().isForbidden());

    }



}
