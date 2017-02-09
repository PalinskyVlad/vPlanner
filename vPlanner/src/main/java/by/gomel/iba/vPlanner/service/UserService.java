package by.gomel.iba.vPlanner.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.User;

@Stateless
public class UserService {
	
	@PersistenceContext(unitName = "PersistenceUnit")
    private EntityManager entityManager;
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User authentiacte(String userId, String password) {
    	User user = entityManager.createNamedQuery("user.authenticate", User.class).setParameter("userId", userId).getSingleResult();
    	if (user.getPassword().equals(password)) {
    		return user;
    	}
		System.out.println(user);
    	return null;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void add(User user) {
    	entityManager.persist(user);
    }
   
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
    	User user = findById(id);
    	entityManager.remove(user);
    }
    
}
