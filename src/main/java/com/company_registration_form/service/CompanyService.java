package com.company_registration_form.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company_registration_form.dto.AddressRequest;
import com.company_registration_form.dto.CompanyRequest;
import com.company_registration_form.dto.EmailRequest;
import com.company_registration_form.dto.HrDetailsRequest;
import com.company_registration_form.entity.Address;
import com.company_registration_form.entity.Company;
import com.company_registration_form.entity.Email;
import com.company_registration_form.entity.HrDetails;
import com.company_registration_form.exception.ResourceNotFoundException;
import com.company_registration_form.repository.AddressRepo;
import com.company_registration_form.repository.CompanyRepo;
import com.company_registration_form.repository.EmailRepo;
import com.company_registration_form.repository.HrDetailsRepo;

@Service
public class CompanyService {
	
	    private final CompanyRepo companyRepository;

	    private final AddressRepo addressRepository;

	    private final HrDetailsRepo hrDetailsRepository;

	    private final EmailRepo emailRepository;

		public CompanyService(CompanyRepo companyRepository, AddressRepo addressRepository,
				HrDetailsRepo hrDetailsRepository, EmailRepo emailRepository) {
			super();
			this.companyRepository = companyRepository;
			this.addressRepository = addressRepository;
			this.hrDetailsRepository = hrDetailsRepository;
			this.emailRepository = emailRepository;
		}
			
			 // CREATE COMPANY

		    @Transactional
		    public Company create(
		            CompanyRequest request) {

		        if (companyRepository
		                .existsByRegistrationNo(
		                        request.registrationNo())) {

		            throw new IllegalArgumentException(
		                    "Company registration number already exists"
		            );
		        }


		        Company company =
		                new Company();

		        company.setCompanyName(
		                request.companyName()
		        );

		        company.setRegistartionNo(
		                request.registrationNo()
		        );


		        // Addresses

		        if (request.addresses() != null) {

		            for (AddressRequest requestAddress
		                    : request.addresses()) {

		                Address address =
		                        convertAddress(requestAddress);

		                company.addAddresses(address);
		            }
		        }


		        // HR

		        if (request.hrDetails() != null) {

		            HrDetails hr =
		                    new HrDetails();

		            updateHr(
		                    hr,
		                    request.hrDetails()
		            );

		            company.setHrDetails(hr);
		        }


		        return companyRepository.save(company);
		    }


		    // GET BY ID

		    @Transactional(readOnly = true)
		    public Company getById(Long id) {

		        return companyRepository
		                .findById(id)
		                .orElseThrow(() ->
		                        new ResourceNotFoundException(
		                                "Company not found with id: "
		                                        + id
		                        )
		                );
		    }


		    // GET ALL

		    @Transactional(readOnly = true)
		    public List<Company> getAll() {

		        return companyRepository.findAll();
		    }


		    // UPDATE

		    @Transactional
		    public Company update(
		            Long id,
		            CompanyRequest request) {

		        Company company =
		                getById(id);


		        company.setCompanyName(
		                request.companyName()
		        );

		        company.setRegistartionNo(
		                request.registrationNo()
		        );


		        // Remove existing addresses

		        company.getAddresses().clear();


		        // Add new addresses

		        if (request.addresses() != null) {

		            for (AddressRequest requestAddress
		                    : request.addresses()) {

		                Address address =
		                        convertAddress(requestAddress);

		                company.addAddresses(address);
		            }
		        }


		        // HR

		        if (request.hrDetails() != null) {

		            HrDetails hr =
		                    company.getHrDetails();

		            if (hr == null) {

		                hr = new HrDetails();

		                company.setHrDetails(hr);
		            }

		            updateHr(
		                    hr,
		                    request.hrDetails()
		            );
		        }


		        return companyRepository.save(company);
		    }


		    // DELETE COMPANY

		    @Transactional
		    public void delete(Long id) {

		        Company company =
		                getById(id);

		        companyRepository.delete(company);
		    }


		    // ADD ADDRESS

		    @Transactional
		    public Address addAddress(
		            Long companyId,
		            AddressRequest request) {

		        Company company =
		                getById(companyId);


		        Address address =
		                convertAddress(request);


		        company.addAddresses(address);

		        companyRepository.save(company);


		        return address;
		    }


		    // DELETE ADDRESS

		    @Transactional
		    public void deleteAddress(
		            Long companyId,
		            Long addressId) {

		        Company company =
		                getById(companyId);


		        Address address =
		                addressRepository
		                        .findById(addressId)
		                        .orElseThrow(() ->
		                                new ResourceNotFoundException(
		                                        "Address not found: "
		                                                + addressId
		                                )
		                        );


		        if (!address.getCompany()
		                .getId()
		                .equals(company.getId())) {

		            throw new ResourceNotFoundException(
		                    "Address does not belong to company"
		            );
		        }


		        company.removeAddress(address);

		        companyRepository.save(company);
		    }


		    // ADD EMAIL

		    @Transactional
		    public Email addEmail(
		            Long companyId,
		            EmailRequest request) {

		        Company company =
		                getById(companyId);


		        HrDetails hr =
		                company.getHrDetails();


		        if (hr == null) {

		            throw new ResourceNotFoundException(
		                    "HR details not found"
		            );
		        }

		        Email email = new Email();

		        hr.addEmail(email);

		        hrDetailsRepository.save(hr);

		        return email;
		    }


		    // DELETE EMAIL

		    @Transactional
		    public void deleteEmail(
		            Long companyId,
		            Long emailId) {

		        Company company =
		                getById(companyId);


		        HrDetails hr =
		                company.getHrDetails();


		        if (hr == null) {

		            throw new ResourceNotFoundException(
		                    "HR details not found"
		            );
		        }


		        Email email = emailRepository
		                        .findById(emailId)
		                        .orElseThrow(() ->
		                                new ResourceNotFoundException("Email not found: "+ emailId)
		                                );
		    

		        if (!email.getHrDetails()
		                .getId()
		                .equals(hr.getId())) {

		            throw new ResourceNotFoundException(
		                    "Email does not belong to company"
		            );
		        }


		        hr.removeEmail(email);

		        hrDetailsRepository.save(hr);
		    }


		    // Convert Address DTO -> Entity

		    private Address convertAddress(
		            AddressRequest request) {

		        Address address =
		                new Address();

		        address.setType(
		                request.type()
		        );

		        address.setHouseNo(
		                request.houseNo()
		        );

		        address.setStreet(
		                request.street()
		        );

		        address.setLandmark(
		                request.landmark()
		        );

		        address.setCity(
		                request.city()
		        );

		        address.setState(
		                request.state()
		        );

		        address.setCountry(
		                request.country()
		        );

		        address.setZipCode(
		                request.zipCode()
		        );

		        return address;
		    }


		    // Update HR

		    private void updateHr(
		            HrDetails hr,
		            HrDetailsRequest request) {

		        hr.setFirstName(
		                request.firstName()
		        );

		        hr.setMiddleName(
		                request.middleName()
		        );

		        hr.setLastName(
		                request.lastName()
		        );

		        hr.setUserName(
		                request.userName()
		        );

		        hr.setGender(
		                request.gender()
		        );

		        hr.setDateOfBirth(
		                request.dateOfBirth()
		        );


		        // Remove old emails

		        hr.getEmails().clear();


		        // Add new emails

		        if (request.emails() != null) {

		            for (String emailValue
		                    : request.emails()) {

		                Email email = new Email();

		                email.setEmail(
		                        emailValue
		                );

		                hr.addEmail(email);
		            }
		        }
		    }
		}