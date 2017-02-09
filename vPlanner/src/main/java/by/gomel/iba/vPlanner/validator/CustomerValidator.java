package by.gomel.iba.vPlanner.validator;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import by.gomel.iba.vPlanner.entity.Customer;
import by.gomel.iba.vPlanner.service.CustomerService;
import by.gomel.iba.vPlanner.validator.annotation.CustomerValid;

public class CustomerValidator implements ConstraintValidator<CustomerValid, Customer>{

	@EJB
	private CustomerService customerService;
	
	@Override
	public void initialize(CustomerValid customerValid) {

	}

	@Override
	public boolean isValid(Customer customer, ConstraintValidatorContext arg1) {
		if (customer.getEmail() == null || customer.getFirstName() == null || customer.getLastName() == null || customer.getResponse() == null) {
			arg1.buildConstraintViolationWithTemplate("customer error");
			return false;
		}
		return true;
	}

}
