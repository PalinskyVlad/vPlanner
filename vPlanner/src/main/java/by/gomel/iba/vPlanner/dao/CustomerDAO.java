package by.gomel.iba.vPlanner.dao;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Customer;

@Stateless
public class CustomerDAO extends AbstractDAO<Customer> {
	
    @PersistenceContext(unitName = "PersistenceUnit")
    private EntityManager entityManager;

	public CustomerDAO() {
		super(Customer.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
