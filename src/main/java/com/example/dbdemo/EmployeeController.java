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
    private EmployeeService service;


    @GetMapping
    public ModelAndView findAll() {

        List<Employee> theList = service.getEmployees();
        ModelAndView modelAndView = new ModelAndView("employeeList");
        modelAndView.addObject("employees", theList);
        return modelAndView;
    }
    @GetMapping("/filter")
    public ModelAndView findByExample(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
    HttpServletRequest request, Model model) throws IOException {

        List<Employee> theList = service.searchEmployees(employee);
        ModelAndView modelAndView = new ModelAndView("employeeList");
        modelAndView.addObject("employees", theList);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
                                     HttpServletRequest request, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView("employeeForm");
        if(bindingResult.hasErrors()) {
            System.out.println("There was a error " + bindingResult);
            modelAndView = new ModelAndView("searchForm");
        }
        else {
            modelAndView = new ModelAndView("searchForm");
            modelAndView.addObject("employee", employee);
        }
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

    @GetMapping( value={"/employee", "/employee/{id}"})
    public ModelAndView employeeForm(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
                                     HttpServletRequest request, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView("employeeForm");
        if(bindingResult.hasErrors()) {
            System.out.println("There was a error " + bindingResult);
            modelAndView = new ModelAndView("employeeList");
        }
        else {
            Employee theEmp = employee;
            if (employee.getId() != null) {
                theEmp = findById(employee.getId());
            }
            modelAndView = new ModelAndView("employeeForm");
            modelAndView.addObject("employee", theEmp);
        }
        return modelAndView;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createOrUpdate(@ModelAttribute Employee employee) {
        Preconditions.checkNotNull(employee);
        Employee emp = service.addOrModifyEmployee(employee);
        ModelAndView modelAndView = new ModelAndView("employeeResult");

        modelAndView.addObject("employee", emp);

        return modelAndView;
    }

    @GetMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView delete(@PathVariable("id") Long id) {

        Optional<Employee> employee = RestPreconditions.checkFound(service.getSingleEmployee(id));
        if (!employee.isPresent()) {
            throw new RuntimeException("Employee Id" + id + " not found.");
        }

        Employee theEmp = employee.get();
        ModelAndView modelAndView = new ModelAndView("deleteResult");
        modelAndView.addObject("employee", theEmp);
        service.deleteEmployee(id);
        return modelAndView;
    }

}