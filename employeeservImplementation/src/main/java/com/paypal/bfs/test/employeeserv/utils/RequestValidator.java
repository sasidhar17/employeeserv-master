package com.paypal.bfs.test.employeeserv.utils;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeDAO;
import com.paypal.bfs.test.employeeserv.exception.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class RequestValidator {

	@Autowired
	private EmployeeDAO employeeDao;

	private void validateAddress(Address address) throws ValidationException {

		if (Objects.isNull(address))
			throw new ValidationException("address cannot be null/empty");
		if (StringUtils.isEmpty(address.getLine1()))
			throw new ValidationException("line 1 cannot be null/empty");
		if (StringUtils.isEmpty(address.getCity()))
			throw new ValidationException("city cannot be null/empty");
		if (StringUtils.isEmpty(address.getState()))
			throw new ValidationException("state cannot be null/empty");
		if (StringUtils.isEmpty(address.getCountry()))
			throw new ValidationException("country cannot be null/empty");
		if (address.getZipCode() == null)
			throw new ValidationException("zip code cannot be null");
	}

	public void validateCreateRequest(Employee employee) throws ValidationException {

		if (Objects.isNull(employee)) {
			throw new ValidationException("request cannot be null");
		}

		if (StringUtils.isEmpty(employee.getFirstName())) {
			throw new ValidationException("first name cannot be null/empty");
		}

		if (StringUtils.isEmpty(employee.getLastName())) {
			throw new ValidationException("last name cannot be null/empty");
		}

		if (StringUtils.isEmpty(employee.getDateOfBirth())) {
			throw new ValidationException("date of birth cannot be null/empty");
		}
		validateAddress(employee.getAddress());

		if (employee.getId()!= null && recordAlreadyExists(employee)) {
			throw new ValidationException("Employee with given id already exists");
		}
	}

	private boolean recordAlreadyExists(Employee employee) {
		Optional<Employee> employeeEntity = employeeDao.fetchById(employee.getId());
		return employeeEntity.isPresent();
	}
}