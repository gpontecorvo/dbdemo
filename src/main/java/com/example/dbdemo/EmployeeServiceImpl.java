package com.example.dbdemo;


import com.example.dbdemo.data.Employee;
import com.example.dbdemo.data.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

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
    public List<Employee> searchEmployees(Employee searchEmployee) {

        if (searchEmployee.getId() != null) {
            searchEmployee.setId(null);
        }
//        create example

        Example<Employee> employeeExample = makeSearchExample(searchEmployee);

//        ExampleMatcher.
        //calling QueryByExampleExecutor#findAll(Example)
        Iterable<Employee> employees = employeeRepository.findAll(employeeExample);

//        List<Employee> employees = employeeRepository.findByLastName(searchEmployee.getLastName());
        return (List<Employee>) employees;
    }

    private Example<Employee> makeSearchExample(Employee searchEmployee) {
        Employee emp = new Employee();
        emp.setAge(getExampleValue(searchEmployee.getAge()));
        emp.setFirstName(getExampleValue(searchEmployee.getFirstName()));
        emp.setLastName(getExampleValue(searchEmployee.getLastName()));
        emp.setEmail(getExampleValue(searchEmployee.getEmail()));
        emp.setTitle(null);
        emp.setId(null);
        return Example.of(emp);

    }

    private Integer getExampleValue(Integer anInt) {
        return (anInt != null && anInt > 0 ? anInt : null);
    }

    private String getExampleValue(String str) {
        return (str != null && str.trim().length() > 0 ? str.trim() : null);
    }

    @Override
    @Transactional
    public Employee addOrModifyEmployee(Employee employee) {

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
