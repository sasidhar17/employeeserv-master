package com.paypal.bfs.test.employeeserv.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeDAO;
import com.paypal.bfs.test.employeeserv.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.exception.ValidationException;
import com.paypal.bfs.test.employeeserv.utils.RequestValidator;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	private EmployeeDAO employeedao;

	@Autowired
	RequestValidator requestValidator;

	@Override
	public ResponseEntity<Employee> employeeGetById(Integer id) throws EmployeeNotFoundException {

		Optional<Employee> employee = employeedao.fetchById(id);

		if (employee.isEmpty()) {
			throw new EmployeeNotFoundException("Employee with id " + id + " not found");
		}

		return new ResponseEntity<>(employee.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Employee> createEmployee(Employee employee) throws ValidationException {

		requestValidator.validateCreateRequest(employee);

		Employee createdEmployee = employeedao.saveEmployee(employee);

		return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	}

}
