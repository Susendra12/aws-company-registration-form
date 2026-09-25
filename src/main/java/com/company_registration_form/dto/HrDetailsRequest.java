package com.company_registration_form.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record HrDetailsRequest(
		
		@NotBlank
		String firstName,
		
		String middleName,
		
		@NotBlank
		String lastName,
		
	    String userName,
		
		String gender,
		
		LocalDate dateOfBirth,
		
		List<@Email String> emails) {

	

}
