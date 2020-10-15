package de.bsi.mongo;

import java.time.Instant;

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
    private Instant hireDate;

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

	public Instant getHireDate() {
		return hireDate;
	}

	public void setHireDate(Instant hireDate) {
		this.hireDate = hireDate;
	}

}
