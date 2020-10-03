package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.domain.Employee;
import com.takeaway.challenge.domain.EmployeeEvent;
import com.takeaway.challenge.domain.enums.CrudOp;
import com.takeaway.challenge.exception.TakeawayError;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.req.EmployeeUpdateReq;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.service.EmployeeEventService;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.util.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentService departmentService;
    private final EmployeeEventService employeeEventService;

    public EmployeeServiceImpl(
            final EmployeeRepository repository,
            final DepartmentService departmentService,
            final EmployeeEventService employeeEventService
    )
    {
        this.repository = repository;
        this.departmentService = departmentService;
        this.employeeEventService = employeeEventService;
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

        var newEmployee = repository.save(new Employee(add, department.get()));
        var employeeEvent = new EmployeeEvent(newEmployee, CrudOp.ADD);
        employeeEventService.sendMessage(employeeEvent);

        return newEmployee;
    }

    @Override
    public Optional<Employee> find(final UUID id) {
        Assert.notNull(id, "employeeId cannot be null");
        return repository.findOneById(id);
    }

    @Override
    public void delete(final UUID id) {
        Assert.notNull(id, "employeeId cannot be null");
        var employee = find(id);
        if(employee.isEmpty()) {
            throw new TakeawayException(TakeawayError.GEN_01, HttpStatus.NOT_FOUND);
        }

        repository.deleteById(id);

        var employeeEvent = new EmployeeEvent(employee.get(), CrudOp.DELETE);
        employeeEventService.sendMessage(employeeEvent);
    }

    @Override
    public Employee update(final UUID uuid, final EmployeeUpdateReq update) {
        Assert.notNull(uuid, "uuid cannot be null");
        Assert.notNull(update, "EmployeeUpdateReq cannot be null");
        var employeeOpt = find(uuid);
        if(employeeOpt.isEmpty()) {
            throw new TakeawayException(TakeawayError.GEN_01, HttpStatus.NOT_FOUND);
        }

        var employee = employeeOpt.get();
        var updatedDeptId = update.getDepartmentId();
        var updatedBirthday = update.getBirthday();
        var updatedName = update.getName();
        var updatedEmail = update.getEmail();

        if(updatedDeptId.isPresent()) {
            var updatedDepartment = departmentService.find(updatedDeptId.get());
            if(updatedDepartment.isEmpty()) {
                throw new TakeawayException(TakeawayError.GEN_01, HttpStatus.NOT_FOUND);
            }
            employee.setDepartment(updatedDepartment.get());
        }

        updatedBirthday.ifPresent(employee::setBirthday);
        updatedName.ifPresent(employee::setName);

        if(updatedEmail.isPresent()) {
            var currentEmail = employee.getEmail();
            if(!currentEmail.equals(updatedEmail.get())) {
                if (employeeWithEmailAlreadyExists(updatedEmail.get())) {
                    throw new TakeawayException(TakeawayError.E_01, HttpStatus.FORBIDDEN);
                }
                employee.setEmail(updatedEmail.get());
            }
        }

        employee.setUpdated(ZonedDateTime.now(ZoneId.of("UTC")));

        var updatedEmployee = repository.save(employee);
        var employeeEvent = new EmployeeEvent(updatedEmployee, CrudOp.UPDATE);
        employeeEventService.sendMessage(employeeEvent);

        return updatedEmployee;
    }

    private boolean employeeWithEmailAlreadyExists(final String email) {
        var employee = repository.findOneByEmail(email);
        return employee.isPresent();
    }
}
