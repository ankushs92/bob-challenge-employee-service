package com.takeaway.challenge.repository;

import com.takeaway.challenge.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findOneByName(String name);

    Optional<Department> findOneById(int id);

}
