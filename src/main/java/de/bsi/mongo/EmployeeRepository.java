package de.bsi.mongo;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

//String: Type of Employee ID.
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	 
    Employee findByEmpNo(String empNo);
 
    List<Employee> findByFullNameLike(String fullName);
 
    List<Employee> findByHireDateGreaterThan(Instant hireDate);
}