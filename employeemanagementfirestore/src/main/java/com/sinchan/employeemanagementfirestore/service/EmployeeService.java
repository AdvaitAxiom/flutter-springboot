package com.sinchan.employeemanagementfirestore.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.sinchan.employeemanagementfirestore.FirebaseConfig;
import com.sinchan.employeemanagementfirestore.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class EmployeeService {

    private final String COLLECTION_NAME = "employees";

    public List<Employee> getAllEmployees() throws ExecutionException, InterruptedException {
        Firestore db = FirebaseConfig.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Employee> employeeList = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            employeeList.add(doc.toObject(Employee.class));
        }
        return employeeList;
    }

    public Employee getEmployeeById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirebaseConfig.getFirestore();

        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();
        if (snapshot.exists()) {
            return snapshot.toObject(Employee.class);
        } else {
            return null;
        }
    }

    public Employee addEmployee(Employee employee) throws ExecutionException, InterruptedException {
        Firestore db = FirebaseConfig.getFirestore();

        DocumentReference newDoc = db.collection(COLLECTION_NAME).document();
        employee.setEmpid(newDoc.getId());
        ApiFuture<WriteResult> result = newDoc.set(employee);
        result.get();
        return employee;
    }

    public Employee updateEmployee(String id, Employee updatedEmployee) throws ExecutionException, InterruptedException {
        Firestore db = FirebaseConfig.getFirestore();

        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();
        if (snapshot.exists()) {
            updatedEmployee.setEmpid(id);
            ApiFuture<WriteResult> result = docRef.set(updatedEmployee);
            result.get();
            return updatedEmployee;
        } else {
            return null;
        }
    }

    public boolean deleteEmployee(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirebaseConfig.getFirestore();

        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();
        if (snapshot.exists()) {
            ApiFuture<WriteResult> result = docRef.delete();
            result.get();
            return true;
        } else {
            return false;
        }
    }
}
