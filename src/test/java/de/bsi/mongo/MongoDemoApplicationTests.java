package de.bsi.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.bsi.mongo.model.Address;
import de.bsi.mongo.model.Employee;

@SpringBootTest
class MongoDemoApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepo;
	
	@BeforeEach
	void initDatabase() {
		employeeRepo.deleteAll();
		
		var address1 = new Address("Musterstadt", "Musterstr. 123");
		var uwe = new Employee(
				"123", "Uwe Mustermann", LocalDate.now(), address1);		
		var elmar = new Employee(
				"456", "Elmar Brauch", LocalDate.parse("2010-08-01"), null);
		
		employeeRepo.insert(List.of(uwe, elmar));
	}
	
	@Test
	void addOneEmployee() {
		var bill = new Employee(null, "Bill Gates", null, null);
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
		assertTrue(employeeRepo.findByHireDateGreaterThan(LocalDate.now()).isEmpty());
		var yesterday = LocalDate.now().minusDays(1);
		assertEquals(1, employeeRepo.findByHireDateGreaterThan(yesterday).size());
	}
}
