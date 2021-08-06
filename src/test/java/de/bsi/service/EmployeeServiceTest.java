package de.bsi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import de.bsi.mongo.Employee;
import de.bsi.mongo.EmployeeRepository;

@SpringBootTest(classes = EmployeeService.class)
class EmployeeServiceTest {

	@MockBean private EmployeeRepository repoMock;
	@MockBean private EmployeePrinter printerMock;
	@Autowired private EmployeeService service;
	
	private Employee testEmployee = new Employee(); 
	
	@BeforeEach
	void prepareMocks() {
		testEmployee.setEmpNo("1898");
		testEmployee.setFullName("Elmar Brauch");
		testEmployee.setHireDate(Instant.now());
		
		when(repoMock.findAll(any(Pageable.class)))
			.thenReturn(new PageImpl<>(List.of(testEmployee, testEmployee)))
			.thenReturn(new PageImpl<>(List.of(testEmployee)));
	}
	
	@Test
	void testPrintEmployees() {
		service.printEmployees(2);
		verify(printerMock, times(2)).print(testEmployee);
	}
	
	@Test
	void testPrintEmployees2() {
		// Execute previous test for 2 mock invocations.
		testPrintEmployees();
		
		// 2nd thenReturn is used, which sends List with only 1 Employee.
		service.printEmployees(1);
		verify(printerMock, times(3)).print(testEmployee);
		// 2nd thenReturn is used again, that is why times is increased by 1.
		service.printEmployees(2);
		verify(printerMock, times(4)).print(testEmployee);
	}
	
	// TODO Use Captor
	// TODO Show different Mock responses
	// TODO Throw Exception

}
