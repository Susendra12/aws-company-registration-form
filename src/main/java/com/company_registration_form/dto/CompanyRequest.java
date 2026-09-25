package com.company_registration_form.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(
		
		@NotBlank
		String companyName,
		
		@NotBlank
		String registrationNo,
		
		@Valid
		List<AddressRequest> addresses,
		
		@Valid
		HrDetailsRequest hrDetails) {

}
