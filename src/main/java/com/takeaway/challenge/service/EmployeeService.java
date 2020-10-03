package com.takeaway.challenge.service;

import com.takeaway.challenge.domain.Employee;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.req.EmployeeUpdateReq;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeService {

    Employee create(EmployeeAddReq add);

    Optional<Employee> find(UUID employeeId);

    void delete(UUID employeeId);

    Employee update(UUID uuid, EmployeeUpdateReq update);

}
