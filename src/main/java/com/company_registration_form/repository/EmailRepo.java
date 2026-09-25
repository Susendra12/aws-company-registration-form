package com.company_registration_form.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_registration_form.entity.Email;


public interface EmailRepo extends JpaRepository<Email, Long> {

	
}
