package com.sinchan.employeemanagementmysql.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinchan.employeemanagementmysql.model.Employee;
import com.sinchan.employeemanagementmysql.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(Employee emp) {
        return employeeRepository.save(emp);
    }

    public Employee updateEmployee(Employee emp) {
        return employeeRepository.save(emp);
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}
