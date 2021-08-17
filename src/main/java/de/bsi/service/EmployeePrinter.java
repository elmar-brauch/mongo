package de.bsi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.bsi.mongo.model.Employee;

@Component
public class EmployeePrinter {
	
	private static final String EMP_TEXT = "{0} : {1} hired at {2}";
	
	private Logger log = LoggerFactory.getLogger(EmployeePrinter.class);
	
	public void print(Employee employee) {
		log.info(EMP_TEXT,
				employee.getEmpNo(), 
				employee.getFullName(), 
				employee.getHireDate());
	}

}
