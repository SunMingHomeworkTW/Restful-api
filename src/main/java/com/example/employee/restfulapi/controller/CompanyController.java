package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.exceptions.IdNotFoundException;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Company getCompanyById(@PathVariable long id) {
        return companyRepository.findOne(id);
    }

    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    List<Employee> getEmployeesByCompanyId(@PathVariable long id) {
        return companyRepository.findOne(id).getEmployees();
    }

    @GetMapping("/page/{page}/pageSize/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    Page<Company> getAllCompaniesPaging(@PathVariable int page, @PathVariable int pageSize) {
        return companyRepository.findAll(new PageRequest(page,pageSize));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    Company createCompany(@RequestBody Company company){
        return companyRepository.save(company);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Company updateCompanyById(@PathVariable long id,@RequestBody Company company){
        Company existedCompany=companyRepository.findOne(id);
        if(existedCompany!=null){
            existedCompany.setCompanyName(company.getCompanyName());
            existedCompany.setEmployeesNumber(company.getEmployeesNumber());
            return companyRepository.save(existedCompany);
        }
        else {
            throw new IdNotFoundException("company",id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Company deleteCompanyById(@PathVariable long id){
        Company existedCompany=companyRepository.findOne(id);
        if(existedCompany!=null){
            companyRepository.delete(id);
            return existedCompany;
        }
        else {
            throw new IdNotFoundException("company",id);
        }
    }
}
