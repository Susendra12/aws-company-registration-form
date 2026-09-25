package com.company_registration_form.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_registration_form.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
