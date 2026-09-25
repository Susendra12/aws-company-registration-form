package com.company_registration_form.dto;

public record AddressRequest(
		
		String type,
		String houseNo, 
		String street,
		String landmark,
		String city,
		String state,
		String country,
		String zipCode) {

}
