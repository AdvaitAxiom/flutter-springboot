package com.sinchan.employeemanagementfirestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinchan.employeemanagementfirestore.model.Employee;
import com.sinchan.employeemanagementfirestore.service.EmployeeService;

import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/emp")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() throws ExecutionException, InterruptedException {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) throws ExecutionException, InterruptedException {
        Employee emp = employeeService.getEmployeeById(id);
        if (emp != null) {
            return ResponseEntity.ok(emp);
        } else {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) throws ExecutionException, InterruptedException {
        Employee savedEmp = employeeService.addEmployee(employee);
        return ResponseEntity.ok(savedEmp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) throws ExecutionException, InterruptedException {
        Employee updated = employeeService.updateEmployee(id, updatedEmployee);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) throws ExecutionException, InterruptedException {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            return ResponseEntity.ok("Employee deleted");
        } else {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }
}
