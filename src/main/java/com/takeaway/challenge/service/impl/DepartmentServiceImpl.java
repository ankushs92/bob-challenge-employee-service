package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.exception.TakeawayError;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.req.DepartmentAddReq;
import com.takeaway.challenge.service.DepartmentService;
import com.takeaway.challenge.util.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    DepartmentServiceImpl(final DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Department create(final DepartmentAddReq departmentAddReq) {
        Assert.notNull(departmentAddReq, "departmentAddReq cannot be null");
        var department = repository.findOneByName(departmentAddReq.getName());
        if(department.isPresent()) {
            throw new TakeawayException(TakeawayError.D_01, HttpStatus.FORBIDDEN);
        }
        return repository.save(new Department(departmentAddReq));
    }

    @Override
    public Optional<Department> find(final int id) {
        return repository.findOneById(id);
    }

    @Override
    public void delete(final int id) {
        repository.deleteById(id);
    }
}
