package com.sinchan.employeemanagementmysql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private String empid;
    private String empname;
    private String deptname;

    public Employee() {
        this.empid = UUID.randomUUID().toString();
    }

    public Employee(String empname, String deptname) {
        this.empid = UUID.randomUUID().toString();
        this.empname = empname;
        this.deptname = deptname;
    }

    public String getEmpid() { return empid; }
    public void setEmpid(String empid) { this.empid = empid; }

    public String getEmpname() { return empname; }
    public void setEmpname(String empname) { this.empname = empname; }

    public String getDeptname() { return deptname; }
    public void setDeptname(String deptname) { this.deptname = deptname; }
}
