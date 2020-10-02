package com.takeaway.challenge.service;

import com.takeaway.challenge.domain.Employee;
import com.takeaway.challenge.req.EmployeeAddReq;

import java.util.UUID;

public interface EmployeeService {

    Employee create(EmployeeAddReq add);

    Employee find(UUID employeeId);

    boolean delete(UUID employeeId);

}
