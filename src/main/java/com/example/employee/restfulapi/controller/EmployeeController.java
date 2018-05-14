package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.exceptions.IdNotFoundException;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Employee getEmployeeById(@PathVariable long id) {
        return employeeRepository.findOne(id);
    }


    @GetMapping("/page/{page}/pageSize/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    Page<Employee> getAllEmployeesPaging(@PathVariable int page, @PathVariable int pageSize) {
        return employeeRepository.findAll(new PageRequest(page,pageSize));
    }

    @GetMapping("/gender/{gender}")
    @ResponseStatus(HttpStatus.OK)
    List<Employee> getEmployeeByGender(@PathVariable String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    Employee createEmployee(@RequestBody Employee employee){
        Employee newEmployee=employeeRepository.save(employee);
        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Employee updateEmployeeById(@PathVariable long id,@RequestBody Employee employee){
        Employee existedEmployee=employeeRepository.findOne(id);
        if(existedEmployee!=null){
            existedEmployee.setAge(employee.getAge());
            existedEmployee.setCompanyId(employee.getCompanyId());
            existedEmployee.setGender(employee.getGender());
            existedEmployee.setName(employee.getName());
            existedEmployee.setSalary(employee.getSalary());
            return employeeRepository.save(existedEmployee);
        }
        else {
            throw new IdNotFoundException("employee",id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Employee deleteEmployeeById(@PathVariable long id){
        Employee existedEmployee=employeeRepository.findOne(id);
        if(existedEmployee!=null){
            employeeRepository.delete(id);
            return existedEmployee;
        }
        else {
            throw new IdNotFoundException("employee",id);
        }
    }
}
