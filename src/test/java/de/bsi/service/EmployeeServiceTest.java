package de.bsi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import de.bsi.mongo.EmployeeRepository;
import de.bsi.mongo.model.Employee;

@SpringBootTest(classes = EmployeeService.class)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@MockBean private EmployeeRepository repoMock;
	@MockBean private EmployeePrinter printerMock;
	@Autowired private EmployeeService service;
	
	private Employee testEmployee1;
	private Employee testEmployee2;
	
	@BeforeEach
	void createTestData() {
		testEmployee1 = new Employee("1898", "Elmar Brauch", LocalDate.now(), null);
		testEmployee2 = new Employee("001", "Tony Stark", LocalDate.EPOCH, null);
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
		assertEquals(LocalDate.now(), newEmployee.getHireDate());
	}
}