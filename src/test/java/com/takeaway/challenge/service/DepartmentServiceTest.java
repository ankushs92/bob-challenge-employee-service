package com.takeaway.challenge.service;

import com.takeaway.challenge.ChallengeApplicationTests;
import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.req.DepartmentAddReq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepartmentServiceTest extends ChallengeApplicationTests {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void persistDepartment() {
        var dept = new Department(new DepartmentAddReq("Mathematics"));
        departmentRepository.save(dept);
    }

    @AfterEach
    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    @Test
    public void testCreate_DeptIsEnglish_shouldReturnEnglishDeptWhenQueriedById() {
        var department = departmentService.create(new DepartmentAddReq("English"));
        assertThat(department.getName(), equalTo("English"));
    }

    @Test
    public void testCreate_DeptIsMath_shouldThrowException() {
        assertThrows(TakeawayException.class,
                    () -> departmentService.create(new DepartmentAddReq("Mathematics"))
        );
    }



}
