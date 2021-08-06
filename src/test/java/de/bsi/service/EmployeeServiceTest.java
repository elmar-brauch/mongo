package de.bsi.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import de.bsi.mongo.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {

	@Mock private EmployeeRepository repoMock;
	@Mock private EmployeePrinter printerMock;
	@InjectMocks private EmployeeService service = new EmployeeService();
	
	@Test
	void testPrintEmployees() {
		service.printEmployees(2);
	}

}
