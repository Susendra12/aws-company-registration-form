package com.company_registration_form.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company_registration_form.dto.AddressRequest;
import com.company_registration_form.dto.CompanyRequest;
import com.company_registration_form.dto.EmailRequest;
import com.company_registration_form.entity.Address;
import com.company_registration_form.entity.Company;
import com.company_registration_form.entity.Email;
import com.company_registration_form.service.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {


    private final CompanyService companyService;


    public CompanyController(
            CompanyService companyService) {

        this.companyService =
                companyService;
    }


    // CREATE

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(
            @Valid
            @RequestBody CompanyRequest request) {

        return companyService.create(request);
    }

    @GetMapping("/a")
    public ResponseEntity<String> home(){
    	return new ResponseEntity<String>("Welcome to my world",HttpStatus.ACCEPTED);
    }

    // GET ALL

    @GetMapping
    public List<Company> getAllCompanies() {

        return companyService.getAll();
    }


    // GET BY ID

    @GetMapping("/{id}")
    public Company getCompany(
            @PathVariable Long id) {

        return companyService.getById(id);
    }


    // UPDATE

    @PutMapping("/{id}")
    public Company updateCompany(
            @PathVariable Long id,

            @Valid
            @RequestBody CompanyRequest request) {

        return companyService.update(
                id,
                request
        );
    }


    // DELETE

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(
            @PathVariable Long id) {

        companyService.delete(id);
    }


    // ADD ADDRESS

    @PostMapping("/{companyId}/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public Address addAddress(
            @PathVariable Long companyId,

            @RequestBody AddressRequest request) {

        return companyService.addAddress(
                companyId,
                request
        );
    }


    // DELETE ADDRESS

    @DeleteMapping(
            "/{companyId}/addresses/{addressId}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(

            @PathVariable Long companyId,

            @PathVariable Long addressId) {

        companyService.deleteAddress(
                companyId,
                addressId
        );
    }


    // ADD EMAIL

    @PostMapping("/{companyId}/hr/emails")
    @ResponseStatus(HttpStatus.CREATED)
    public Email addEmail(

            @PathVariable Long companyId,

            @Valid
            @RequestBody EmailRequest request) {

        return companyService.addEmail(companyId, request);
    }


    // DELETE EMAIL

    @DeleteMapping(
            "/{companyId}/hr/emails/{emailId}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmail(

            @PathVariable Long companyId,

            @PathVariable Long emailId) {

        companyService.deleteEmail(
                companyId,
                emailId
        );
    }
}
