package com.example.dbdemo;

import com.example.dbdemo.data.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    @Transactional
    List<Employee> getEmployees();

    @Transactional
    Employee addOrModifyEmployee(Employee employee);

    @Transactional
    Employee updateEmployee(Employee employee);

    @Transactional
    Optional<Employee> getSingleEmployee(long theId);

    @Transactional
    void deleteEmployee(long theId);

    @Transactional
    List<Employee> searchEmployees(Employee searchEmployee);

}

