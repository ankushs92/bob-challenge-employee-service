package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.domain.Employee;
import com.takeaway.challenge.exception.TakeawayError;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.util.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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
        var department = departmentService.find(add.getDepartmentId());
        //The department needs to exist in the database for an employee to be added to it
        if(department.isEmpty()) {
            throw new TakeawayException(TakeawayError.GEN_01, HttpStatus.NOT_FOUND);
        }

        var employee = repository.findOneByEmail(add.getEmail());
        if(employee.isPresent()) {
            throw new TakeawayException(TakeawayError.E_01, HttpStatus.FORBIDDEN);
        }

        return repository.save(new Employee(add, department.get()));
    }

    @Override
    public Optional<Employee> find(final UUID id) {
        Assert.notNull(id, "employeeId cannot be null");
        return repository.findOneById(id);
    }

    @Override
    public void delete(final UUID id) {
        Assert.notNull(id, "employeeId cannot be null");
        repository.deleteById(id);
    }
}
