package com.paypal.bfs.test.employeeserv.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.ValidationException;

@Repository
@Transactional
public class EmployeeDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public Employee saveEmployee(Employee employee) throws ValidationException {

		EmployeeEntity employeeEntity = formEmployeeEntityFromEmployeeResource(employee);
		entityManager.persist(employeeEntity);

		return formEmployeeFromEmployeeEntity(employeeEntity);
	}

	public Optional<Employee> fetchById(Integer id) {

		EmployeeEntity employeeEntity = entityManager.find(EmployeeEntity.class, id);

		if (employeeEntity == null) {
			return Optional.empty();
		}

		return Optional.of(formEmployeeFromEmployeeEntity(employeeEntity));
	}

	private static EmployeeEntity formEmployeeEntityFromEmployeeResource(Employee employee) throws ValidationException {

		EmployeeEntity employeeEntity = new EmployeeEntity();

		employeeEntity.setId(employee.getId());
		employeeEntity.setFirstName(employee.getFirstName());
		employeeEntity.setLastName(employee.getLastName());

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(employee.getDateOfBirth());
		} catch (ParseException e) {
			throw new ValidationException("Provide Date format as yyyy-mm-dd");
		}

		employeeEntity.setDateOfBirth(date);
		employeeEntity.setId(employee.getId());

		employeeEntity.setAddress(formAddressEntityFromAddressResource(employee.getAddress()));

		return employeeEntity;
	}

	private static AddressEntity formAddressEntityFromAddressResource(Address address) {

		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setLine1(address.getLine1());
		addressEntity.setLine2(address.getLine2());
		addressEntity.setCity(address.getCity());
		addressEntity.setState(address.getState());
		addressEntity.setCountry(address.getCountry());
		addressEntity.setZipCode(address.getZipCode());

		return addressEntity;
	}

	private static Employee formEmployeeFromEmployeeEntity(EmployeeEntity employeeEntity) {

		Employee employee = new Employee();
		employee.setId(employeeEntity.getId());
		employee.setFirstName(employeeEntity.getFirstName());
		employee.setLastName(employeeEntity.getLastName());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		employee.setDateOfBirth(dateFormat.format(employeeEntity.getDateOfBirth()));
		employee.setAddress(formAddressFromAddressEntity(employeeEntity.getAddress()));

		return employee;
	}

	private static Address formAddressFromAddressEntity(AddressEntity addressEntity) {

		Address address = new Address();
		address.setId(addressEntity.getId());
		address.setLine1(addressEntity.getLine1());
		address.setLine2(addressEntity.getLine2());
		address.setCity(addressEntity.getCity());
		address.setState(addressEntity.getState());
		address.setCountry(addressEntity.getCountry());
		address.setZipCode(addressEntity.getZipCode());

		return address;
	}

}
