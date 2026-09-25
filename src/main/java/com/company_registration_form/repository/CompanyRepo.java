package com.company_registration_form.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_registration_form.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, Long> {

	boolean existsByRegistrationNo(String registrationNo);
	
	Optional<Company> findByRegistrationNo(String registrationNo);
}
