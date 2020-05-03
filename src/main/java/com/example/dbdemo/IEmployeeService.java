package com.example.dbdemo;

import com.example.dbdemo.data.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    @Transactional
    List<Employee> getEmployees();

    @Transactional
    Employee addEmployee(Employee employee);

    @Transactional
    Employee updateEmployee(Employee employee);

    @Transactional
    Optional<Employee> getSingleEmployee(long theId);

    @Transactional
    void deleteEmployee(long theId);
}
