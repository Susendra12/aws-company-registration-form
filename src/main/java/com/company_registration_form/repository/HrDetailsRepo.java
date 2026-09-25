package com.company_registration_form.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_registration_form.entity.HrDetails;

public interface HrDetailsRepo extends JpaRepository<HrDetails, Long>{

	Optional<HrDetails> findByCompanyId(Long companyId);
}
