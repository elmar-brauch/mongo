package de.bsi.mongo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Employee")
public class Employee {
	
	@Id
    private String id;
 
    @Indexed(unique = false)
    @Field(value = "Emp_No")
    private String empNo;
 
    private String fullName;
 
    @Field(value = "Hire_Date")
    private LocalDate hireDate;
    
    private Address address;
    
    public Employee() {}
    
    public Employee(String empNo, String fullName, LocalDate hireDate) {
    	this(empNo, fullName, hireDate, null);
    }
    
    public Employee(String empNo, String fullName, LocalDate hireDate, Address address) {
    	this.empNo = empNo;
    	this.fullName = fullName;
    	this.hireDate = hireDate;
    	this.address = address;
    }

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

}
