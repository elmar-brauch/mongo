package de.bsi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.mongodb.MongoExecutionTimeoutException;

import de.bsi.mongo.Employee;
import de.bsi.mongo.EmployeeRepository;

@SpringBootTest(classes = EmployeeService.class)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@MockBean private EmployeeRepository repoMock;
	@MockBean private EmployeePrinter printerMock;
	@Autowired private EmployeeService service;
	
	private Employee testEmployee1 = new Employee();
	private Employee testEmployee2 = new Employee();
	
	@BeforeEach
	void createTestData() {
		testEmployee1.setEmpNo("1898");
		testEmployee1.setFullName("Elmar Brauch");
		testEmployee1.setHireDate(Instant.now());
		testEmployee2.setEmpNo("001");
		testEmployee2.setFullName("Tony Stark");
		testEmployee2.setHireDate(Instant.EPOCH);
	}
	
	@Test 
	void firstNameOf() {
		when(repoMock.findByEmpNo("001"))
			.thenReturn(testEmployee2);
		when(repoMock.findByEmpNo(startsWith("1")))
			.thenReturn(testEmployee1);
		
		assertEquals("Tony", service.firstNameOf("001").get());
		assertEquals("Elmar", service.firstNameOf("111").get());
		assertTrue(service.firstNameOf("abc").isEmpty());
	}
	
	@Test 
	void firstNameOfWithException() {
		when(repoMock.findByEmpNo(any()))
			.thenThrow(MongoExecutionTimeoutException.class);
		
		assertThrows(MongoExecutionTimeoutException.class, 
				() -> service.firstNameOf("001"));
	}
	
	@Test
	void printEmployees() {
		when(repoMock.findAll(any(Pageable.class)))
			.thenReturn(new PageImpl<>(List.of(testEmployee1, testEmployee1)))
			.thenReturn(new PageImpl<>(List.of(testEmployee2)));
		
		service.printEmployees();
		verify(printerMock, times(2)).print(testEmployee1);
		
		service.printEmployees();
		service.printEmployees();
		service.printEmployees();
		verify(printerMock, times(3)).print(testEmployee2);
	}
		
	@Captor ArgumentCaptor<Employee> empCaptor;
	
	@Test
	void storeNewEmployee() {
		service.storeNewEmployee("Parker");
		verify(repoMock).save(empCaptor.capture());
		
		var newEmployee = empCaptor.getValue();
		assertEquals("Mr. PARKER", newEmployee.getFullName());
		assertTrue(newEmployee.getHireDate().isBefore(Instant.now()));
	}
}