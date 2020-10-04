package com.takeaway.challenge.security;

import com.takeaway.challenge.ChallengeApplicationTests;
import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.req.DepartmentAddReq;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.util.Json;
import com.takeaway.challenge.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class AuthorizationTest extends ChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    private Department department = null;

    @BeforeEach
    public void addDepartment() {
        department = departmentService.create(new DepartmentAddReq("Math"));
    }

    //Only testing for one endpoint due to lack of time, similarly we can do for other endpoints as well
    @Test
    public void testAuthorization_AccessEmployeePostEndpointWithValidJwt_shouldReturnOkResponse() throws Exception {
        var jwtToken = JwtUtil.buildToken(user);
        var addEmployeeReq = new EmployeeAddReq("ankush20058@gmail.com", "Ankush", LocalDate.of(1992, 10, 5), department.getId());
        var json = Json.encode(addEmployeeReq);

        var req = MockMvcRequestBuilders
                .post("/employee")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        var result = mockMvc
                    .perform(req)
                    .andExpect(status().isCreated())
                    .andReturn();

        assertThat(result.getResponse().getContentAsString().contains("employeeId"), equalTo(true));
    }

//    @Test
//    public void testAuthorization_AccessEmployeePostEndpointWithInValidJwt_shouldReturnForbidden() throws Exception {
//        var addEmployeeReq = new EmployeeAddReq("ankush20058@gmail.com", "Ankush", LocalDate.of(1992, 10, 5), department.getId());
//        var json = Json.encode(addEmployeeReq);
//
//        var req = MockMvcRequestBuilders
//                .post("/employee")
//                .header("Authorization", "RANDOM JWT VALUE") // <<-- THIS
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(json);
//
//         var s = mockMvc
//                .perform(req)
//                 .andReturn();
//        System.out.println(s.getResponse());
////                .andExpect(status().isForbidden());
//    }

}
