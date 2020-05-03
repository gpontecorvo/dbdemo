package com.example.dbdemo;


import com.example.dbdemo.data.Employee;
import com.example.dbdemo.data.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public List<Employee> getEmployees() {

        // do some business processing here ...
        //now call DAO layer
        return (List<Employee>) employeeRepository.findAll();

    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {

        // do some business processing  here ...
        //now call DAO layer
        Employee save = employeeRepository.save(employee);
        return employee;

    }
    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {

        // do some business processing  here ...
        //now call DAO layer
        Employee save = employeeRepository.save(employee);
        return employee;

    }

    @Override
    @Transactional
    public Optional<Employee> getSingleEmployee(long theId) {

        // do some business processing here ... 
        //now call DAO layer
        return employeeRepository.findById(theId);
    }


    @Override
    @Transactional
    public void deleteEmployee(long theId) {
        // do some business processing here ...
        //now call DAO layer
        employeeRepository.deleteById(theId);
    }
}
