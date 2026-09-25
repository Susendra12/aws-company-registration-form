package com.company_registration_form.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "companies", uniqueConstraints = {@UniqueConstraint(
		         name = "uk_company_registration_no",
		         columnNames = "registration_no")})

public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_name", nullable = false)
	private String companyName;
	
	@Column(name = "comapany_registrationNo", nullable = false, unique = true)
	private String registrationNo;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();
    
	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private HrDetails hrDetails;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRegistartionNo() {
		return registrationNo;
	}

	public void setRegistartionNo(String registartionNo) {
		this.registrationNo = registartionNo;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void addAddresses(Address address) {
		addresses.add(address);
		address.setCompany(this);
	}
	
	public void removeAddress(Address address) {
		addresses.remove(address);
		address.setCompany(null);
	}

	public HrDetails getHrDetails() {
		return hrDetails;
	}

	public void setHrDetails(HrDetails hrDetails) {
		this.hrDetails = hrDetails;
		
		if(hrDetails != null) {
			hrDetails.setCompany(this);
		}
	}
	
}
