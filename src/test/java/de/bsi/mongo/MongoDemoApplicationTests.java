package de.bsi.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MongoDemoApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepo;
	
	@BeforeEach
	void initDatabase() {
		employeeRepo.deleteAll();
		
		var uwe = new Employee();
		uwe.setEmpNo("123");
		uwe.setFullName("Uwe Mustermann");
		uwe.setHireDate(Instant.now());
		
		var elmar = new Employee();
		elmar.setEmpNo("456");
		elmar.setFullName("Elmar Brauch");
		elmar.setHireDate(Instant.parse("2010-08-01T10:15:30.00Z"));
		
		employeeRepo.insert(List.of(uwe, elmar));
	}
	
	@Test
	void addOneEmployee() {
		var bill = new Employee();
		bill.setFullName("Bill Gates");
		employeeRepo.insert(bill);
		assertEquals(3, employeeRepo.count());
	}
	
	@Test
	void findByName() {
		var result = employeeRepo.findByFullNameLike("Elmar");
		assertEquals(1, result.size());
		assertEquals("456", result.get(0).getEmpNo());
	}
	
	@Test
	void findByHireDate() {
		assertTrue(employeeRepo.findByHireDateGreaterThan(Instant.now()).isEmpty());
		var yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
		assertEquals(1, employeeRepo.findByHireDateGreaterThan(yesterday).size());
	}
}
