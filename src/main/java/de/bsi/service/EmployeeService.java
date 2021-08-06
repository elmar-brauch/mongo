package de.bsi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.bsi.mongo.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired private EmployeeRepository repo;
	@Autowired private EmployeePrinter printer;
	
	public void printEmployees(int limit) {
		var employeePage = repo.findAll(Pageable.ofSize(limit));
		employeePage.forEach(printer::print);
	}
	
}
