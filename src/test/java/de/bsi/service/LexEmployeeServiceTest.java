package de.bsi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.bsi.mongo.EmployeeRepository;
import de.bsi.mongo.model.Employee;

class LexEmployeeServiceTest {

	private EmployeeService service;
	
	private Employee testEmployee1;
	private Employee testEmployee2;
	
	@BeforeEach
	void createTestData() {
		testEmployee1 = new Employee("1898", "Elmar Brauch", LocalDate.now(), null);
		testEmployee2 = new Employee("001", "Tony Stark", LocalDate.EPOCH, null);
	}
	
	@Test 
	void firstNameOf() {
		// TODO implement: Mockito returns result. 
	}
	
	//@Test 
	void firstNameOfWithException() {
		// TODO implement: Mockito throws Exception. 
	}
	
	//@Test
	void printEmployees() {
		// TODO implement: Mockito counts calls.
	}
		
	//@Test
	void storeNewEmployee() {
		// TODO implement: Mockito captures parameter.
	}
}