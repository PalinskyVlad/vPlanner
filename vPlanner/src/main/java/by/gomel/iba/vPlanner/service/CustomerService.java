package by.gomel.iba.vPlanner.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Customer;
import by.gomel.iba.vPlanner.entity.User;

@Stateless
public class CustomerService {
	
	@PersistenceContext(unitName = "PersistenceUnit")
    private EntityManager entityManager;
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void add(Customer customer) {
    	entityManager.persist(customer);
    }
   
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
    	Customer customer = findById(id);
    	entityManager.remove(customer);
    }
    
}
