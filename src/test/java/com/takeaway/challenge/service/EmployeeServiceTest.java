package com.takeaway.challenge.service;


import com.takeaway.challenge.ChallengeApplicationTests;
import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.domain.Employee;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.req.DepartmentAddReq;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.req.EmployeeUpdateReq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmployeeServiceTest extends ChallengeApplicationTests {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Department mathDept = null;

    @BeforeEach
    public void persistDepartmentAndEmployee() {
        mathDept = departmentService.create(new DepartmentAddReq("Mathematics"));
        var employee = new Employee(
                new EmployeeAddReq("abcd@gmail.com", "Ankush", LocalDate.of(1992, Month.OCTOBER, 5), mathDept.getId()),
                mathDept
        );
        employeeRepository.save(employee);
    }

    @AfterEach
    public void deleteDepartmentAndEmployee() {
        employeeRepository.deleteAll();
        departmentService.delete(mathDept.getId());
    }

        @Test
    public void testCreate_EmployeeIsJohn_shouldReturnJohnInfoWhenQueriedById() {
        var employeeAdd = new EmployeeAddReq("john@gmail.com", "John", LocalDate.of(1992, Month.OCTOBER, 5), mathDept.getId());
        var employeeId = employeeService.create(employeeAdd).getId();

        var employee = employeeRepository.findOneById(employeeId).get();
        assertThat(employee.getEmail(), equalTo("john@gmail.com"));
        assertThat(employee.getDepartment().getId(), equalTo(mathDept.getId()));
    }

    @Test
    public void testCreate_EmployeeIsJohnButDeptDoesNotExist_shouldThrowException() {
        var invalidDept = 12;
        var employeeAdd = new EmployeeAddReq("john@gmail.com", "John", LocalDate.of(1992, Month.OCTOBER, 5), invalidDept);
        assertThrows(TakeawayException.class,
                    () -> employeeService.create(employeeAdd));

    }

    @Test
    public void testCreate_EmployeeIsAnkushAndEmailAlreadyExists_shouldThrowException() {
        var employeeAdd = new EmployeeAddReq("abcd@gmail.com", "Ankush", LocalDate.of(1992, Month.OCTOBER, 5), mathDept.getId());
        assertThrows(TakeawayException.class,
                () -> employeeService.create(employeeAdd));
    }

    @Test
    public void testDelete_EmployeeIsJohn_shouldReturnEmptyOptionalWhenQueriedById() {
        var employeeAdd = new EmployeeAddReq("john@gmail.com", "John", LocalDate.of(1992, Month.OCTOBER, 5), mathDept.getId());
        var employee = employeeService.create(employeeAdd);
        var employeeId = employee.getId();

        employeeService.delete(employeeId);

        Optional<Employee> employeeOpt = employeeService.find(employeeId);

        assertThat(employeeOpt.isPresent(), equalTo(false));
    }

    @Test
    public void testUpdate_EmployeeIsJohn_shouldReturnUpdatedEmployeeWhenQueriedById() {
        var employeeAdd = new EmployeeAddReq("john@gmail.com", "John", LocalDate.of(1992, Month.OCTOBER, 5), mathDept.getId());
        var employee = employeeService.create(employeeAdd);
        var employeeId = employee.getId();

        var updatedName = "Ankush";
        var employeeUpdateByName = new EmployeeUpdateReq(null, updatedName, null, null);

        var updatedEmployee = employeeService.update(employeeId, employeeUpdateByName);

        Optional<Employee> employeeOpt = employeeService.find(employeeId);

        assertThat(employeeOpt.isPresent(), equalTo(true));
        assertThat(employeeOpt.get().getName(), equalTo(updatedName));
        assertThat(employeeOpt.get().getBirthday(), equalTo(updatedEmployee.getBirthday()));
        assertThat(employeeOpt.get().getId(), equalTo(updatedEmployee.getId()));
        assertThat(employeeOpt.get().getDepartment().getId(), equalTo(updatedEmployee.getDepartment().getId()));


    }


}
