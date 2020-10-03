package com.takeaway.challenge.service;

import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.req.DepartmentAddReq;

import java.util.Optional;

public interface DepartmentService {

    Department create(DepartmentAddReq departmentAddReq);

    Optional<Department> find(int id);

    void delete(int id);

}
