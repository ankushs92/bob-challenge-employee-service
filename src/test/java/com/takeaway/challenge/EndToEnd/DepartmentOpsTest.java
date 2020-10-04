package com.takeaway.challenge.EndToEnd;

import com.takeaway.challenge.ChallengeApplicationTests;
import com.takeaway.challenge.req.DepartmentAddReq;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.util.Json;
import com.takeaway.challenge.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepartmentOpsTest extends ChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddDept_shouldReturnOkResponse() throws Exception {
        var jwtToken = JwtUtil.buildToken(user);
        var deptAddReq = new DepartmentAddReq("Math");
        var json = Json.encode(deptAddReq);

        var req = MockMvcRequestBuilders
                .post("/department")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        var result = mockMvc
                .perform(req)
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(result.getResponse().getContentAsString().contains("departmentId"), equalTo(true));
    }

}
