package com.takeaway.challenge.repository;

import com.takeaway.challenge.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findOneByEmail(String email);

    Optional<Employee> findOneById(UUID uuid);

}
