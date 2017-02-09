package by.gomel.iba.vPlanner.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Manager;

@Stateless
public class ManagerService {
	
	@PersistenceContext(unitName = "PersistenceUnit")
    private EntityManager entityManager;
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Manager findById(Long id) {
        return entityManager.find(Manager.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void add(Manager manager) {
    	entityManager.persist(manager);
    }
   
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
    	Manager manager = findById(id);
    	entityManager.remove(manager);
    }

}
