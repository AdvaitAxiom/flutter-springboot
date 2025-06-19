package com.sinchan.employeemanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sinchan.employeemanagement.model.Employee;

import java.util.*;

@RestController
@RequestMapping("/api/emp")
@CrossOrigin(origins = "*")  // for Flutter CORS
public class EmployeeController {

    private List<Employee> employeeList = new ArrayList<>();

    // GET all
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) {
        Optional<Employee> emp = employeeList.stream()
                .filter(e -> e.getEmpid().equals(id))
                .findFirst();

        return emp.isPresent() ? ResponseEntity.ok(emp.get())
                               : ResponseEntity.status(404).body("Employee not found");
    }

    // POST add new
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        employee.setEmpid(UUID.randomUUID().toString());
        employeeList.add(employee);
        return employee;
    }

    // PUT update by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmpid().equals(id)) {
                updatedEmployee.setEmpid(id);
                employeeList.set(i, updatedEmployee);
                return ResponseEntity.ok(updatedEmployee);
            }
        }
        return ResponseEntity.status(404).body("Employee not found");
    }

    // DELETE by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        boolean removed = employeeList.removeIf(emp -> emp.getEmpid().equals(id));
        return removed ? ResponseEntity.ok("Deleted") : ResponseEntity.status(404).body("Employee not found");
    }
}
