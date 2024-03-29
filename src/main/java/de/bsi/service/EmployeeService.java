package de.bsi.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import de.bsi.mongo.EmployeeRepository;
import de.bsi.mongo.model.Employee;

@Service
public class EmployeeService {

	@Autowired private EmployeeRepository repo;
	@Autowired private EmployeePrinter printer;
	
	public Optional<String> firstNameOf(String empNo) {
		var result = repo.findByEmpNo(empNo);
		if (result != null && result.getFullName() != null)
			return Optional.of(
					result.getFullName().split(" ")[0]);
		return Optional.empty();
	}
	
	public void printEmployees() {
		var employeePage = repo.findAll(Pageable.ofSize(5));
		employeePage.forEach(printer::print);
	}
	
	public void storeNewEmployee(String name) {
		var modifiedName = "Mr. " + name.toUpperCase();
		var employee = new Employee(
				"00X", modifiedName, LocalDate.now());
		repo.save(employee);
	}
	
}
