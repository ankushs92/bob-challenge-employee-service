package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.domain.Employee;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentService departmentService;

    public EmployeeServiceImpl(
            final EmployeeRepository repository,
            final DepartmentService departmentService
    )
    {
        this.repository = repository;
        this.departmentService = departmentService;
    }

    @Override
    public Employee create(final EmployeeAddReq add) {
        Assert.notNull(add, "EmployeeAddReq cannot be null");
        var employee = repository.findOneByEmail(add.getEmail());
        var department = departmentService.find(add.getDepartmentId());
        return null;
    }

    @Override
    public Employee find(final UUID employeeId) {
        Assert.notNull(employeeId, "employeeId cannot be null");

        return null;
    }

    @Override
    public boolean delete(final UUID employeeId) {
        Assert.notNull(employeeId, "employeeId cannot be null");
        return false;
    }
}
