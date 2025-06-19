package com.sinchan.employeemanagement.model;

public class Employee {
    private String empid;
    private String empname;
    private String deptname;

    public Employee() {}

    public Employee(String empid, String empname, String deptname) {
        this.empid = empid;
        this.empname = empname;
        this.deptname = deptname;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}
