package com.example.dbdemo;

import com.example.dbdemo.data.Employee;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
class EmployeeController {

    @Autowired
    private IEmployeeService service;


    @GetMapping
    public ModelAndView findAll() {

        List<Employee> theList = service.getEmployees();
        ModelAndView modelAndView = new ModelAndView("employeeList");
        modelAndView.addObject("employees", theList);
        return modelAndView;
    }

    @GetMapping(value = "/{id}")
    public Employee findById(@PathVariable("id") Long id) {
        Optional<Employee> employee = RestPreconditions.checkFound(service.getSingleEmployee(id));
        if (!employee.isPresent()) {
            throw new RuntimeException("Employee Id" + id + " not found.");
        }
        return employee.get();
    }

    @GetMapping("/employee")
    public ModelAndView employeeForm(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
                                     HttpServletRequest request, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView("employeeForm");
        if(bindingResult.hasErrors()) {
            System.out.println("There was a error " + bindingResult);
            modelAndView = new ModelAndView("employeeList");
        }
        else {
            modelAndView = new ModelAndView("employeeForm");
            modelAndView.addObject("employee", employee);
        }
        return modelAndView;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView create(@ModelAttribute Employee employee) {
        Preconditions.checkNotNull(employee);
        employee.setId(null);
        Employee emp = service.addEmployee(employee);
        ModelAndView modelAndView = new ModelAndView("employeeResult");

        modelAndView.addObject("employee", emp);

        return modelAndView;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Employee resource) {
        Preconditions.checkNotNull(resource);
        RestPreconditions.checkFound(service.getSingleEmployee(resource.getId()));
        service.updateEmployee(resource);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        service.deleteEmployee(id);
    }

}