package com.sinchan.employeemanagementmysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sinchan.employeemanagementmysql.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}

