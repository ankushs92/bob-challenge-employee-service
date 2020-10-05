package com.takeaway.challenge.EndToEnd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takeaway.challenge.ChallengeApplicationTests;
import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.req.DepartmentAddReq;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.response.EmployeeResp;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.util.Json;
import com.takeaway.challenge.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeOpsTest extends ChallengeApplicationTests {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    private Department dept = null;

    @BeforeEach
    public void createDepartment() {
        dept = departmentService.create(new DepartmentAddReq("Math"));
    }

    @Test
    public void testAddEmployee_shouldReturnCreatedResponse() throws Exception {
        var jwtToken = JwtUtil.buildToken(user);
        var employeeAddReq = new EmployeeAddReq("ankush20058@gmail.com", "Ankush", LocalDate.of(1992, 5, 10), dept.getId());
        var json = Json.encode(employeeAddReq);

        var req = MockMvcRequestBuilders
                .post("/employee")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json);

        var response = mockMvc
                .perform(req)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response.contains("employeeId"), equalTo(true));
        assertThat(response.contains("email"), equalTo(true));
        assertThat(response.contains("birthday"), equalTo(true));
        assertThat(response.contains("name"), equalTo(true));
        assertThat(response.contains("id"), equalTo(true));

    }

    @Test
    public void testGetEmployee_shouldReturnOkResponse() throws Exception {
        var jwtToken = JwtUtil.buildToken(user);
        var employeeAddReq = new EmployeeAddReq("ankush20058@gmail.com", "Ankush", LocalDate.of(1992, 5, 10), dept.getId());
        var employee = employeeService.create(employeeAddReq);

        var req = MockMvcRequestBuilders
                .get("/employee/" + employee.getId().toString())
                .header("Authorization", jwtToken);

        var jsonResp = mockMvc
                .perform(req)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var employeeResp = Json.toObject(jsonResp, EmployeeResp.class);

        assertThat(employeeResp.getId(), equalTo(employee.getId()));
        assertThat(employeeResp.getEmail(), equalTo(employee.getEmail()));
        assertThat(employeeResp.getBirthday(), equalTo(employee.getBirthday()));
        assertThat(employeeResp.getDepartment().getId(), equalTo(employee.getDepartment().getId()));
        assertThat(employeeResp.getDepartment().getName(), equalTo(employee.getDepartment().getName()));
    }

    @Test
    public void testDeleteEmployee_shouldReturnNoContentResponse() throws Exception {
        var jwtToken = JwtUtil.buildToken(user);
        var employeeAddReq = new EmployeeAddReq("ankush20058@gmail.com", "Ankush", LocalDate.of(1992, 5, 10), dept.getId());
        var employee = employeeService.create(employeeAddReq);

        var req = MockMvcRequestBuilders
                .delete("/employee/" + employee.getId().toString())
                .header("Authorization", jwtToken);

        mockMvc
                .perform(req)
                .andExpect(status().isNoContent());

    }


    @Test
    public void testUpdateEmployee_shouldReturnOkResponse() throws Exception {
        var jwtToken = JwtUtil.buildToken(user);
        var employeeAddReq = new EmployeeAddReq("ankush20058@gmail.com", "Ankush", LocalDate.of(1992, 5, 10), dept.getId());
        var employee = employeeService.create(employeeAddReq);

        var updatedEmail = "johnny@gmail.com";
        var updatedBirthday = LocalDate.of(1991,5,5);
        var updateEmployeeReq = new EmployeeUpdateTestReq(
                updatedEmail,
                "Ankush",
                updatedBirthday,
                dept.getId()
        );

        var jsonReq = Json.encode(updateEmployeeReq);

        var req = MockMvcRequestBuilders
                .put("/employee/" + employee.getId().toString())
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReq);

        var jsonResp = mockMvc
                .perform(req)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var employeeResp = Json.toObject(jsonResp, EmployeeResp.class);

        assertThat(employeeResp.getId(), equalTo(employee.getId()));
        assertThat(employeeResp.getEmail(), equalTo(updatedEmail));
        assertThat(employeeResp.getBirthday(), equalTo(updatedBirthday));
        assertThat(employeeResp.getDepartment().getId(), equalTo(updateEmployeeReq.getDepartmentId()));
    }


    @Test
    public void testUpdateEmployee_WithDeptThatDoesntExist_shouldReturn404ResourceNotFoundResp() throws Exception {
        var jwtToken = JwtUtil.buildToken(user);
        var employeeAddReq = new EmployeeAddReq("ankush20058@gmail.com", "Ankush", LocalDate.of(1992, 5, 10), dept.getId());
        var employee = employeeService.create(employeeAddReq);

        var updatedEmail = "johnny@gmail.com";
        var updatedBirthday = LocalDate.of(1991,5,5);
        var invalidDeptId = 1111;
        var updateEmployeeReq = new EmployeeUpdateTestReq(
                updatedEmail,
                "Ankush",
                updatedBirthday,
                invalidDeptId
        );

        var jsonReq = Json.encode(updateEmployeeReq);
        var req = MockMvcRequestBuilders
                .put("/employee/" + employee.getId().toString())
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReq);

        mockMvc
                .perform(req)
                .andExpect(status().isNotFound());

    }


    //Could not resuse EmployeeUpdateReq declared in main package because the getters in that class return an Optional.
    private static class EmployeeUpdateTestReq {
        private final String email;
        private final String name;
        private final LocalDate birthday;
        private final Integer departmentId;

        EmployeeUpdateTestReq(
                @JsonProperty("email") final String email,
                @JsonProperty("name") final String name,

                @JsonProperty("birthday")
                final LocalDate birthday,

                @JsonProperty("departmentId") final Integer departmentId
        )
        {
            this.email = email;
            this.name = name;
            this.birthday = birthday;
            this.departmentId = departmentId;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public Integer getDepartmentId() {
            return departmentId;
        }
    }

}
