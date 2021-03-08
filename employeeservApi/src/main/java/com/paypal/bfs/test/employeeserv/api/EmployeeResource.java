package com.paypal.bfs.test.employeeserv.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.paypal.bfs.test.employeeserv.api.model.Employee;

/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

	/**
	 * Retrieves the {@link Employee} resource by id.
	 *
	 * @param id employee id.
	 * @return {@link Employee} resource.
	 * @throws Exception
	 */
	@GetMapping(value = "/v1/bfs/employees/{id}", produces = "application/json")
	ResponseEntity<Employee> employeeGetById(@PathVariable("id") Integer id) throws Exception;

	/**
	 * Creates an employee
	 *
	 * @param employee {@link Employee} resource
	 * @return employee {@link Employee} resource
	 * @throws Exception
	 */
	@PostMapping(value = "/v1/bfs/employees", consumes = "application/json", produces = "application/json")
	ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws Exception;

}
