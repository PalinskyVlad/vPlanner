package by.gomel.iba.vPlanner.service;

import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Employee;
import by.gomel.iba.vPlanner.entity.Manager;

@Stateless
public class EmployeeService {
	
	@PersistenceContext(unitName = "PersistenceUnit")
    private EntityManager entityManager;
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Employee findById(Long id) {
        return entityManager.find(Employee.class , id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Set<Employee> findByManagerId(long managerId) {
    	return entityManager.find(Manager.class, managerId).getEmployees();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void add(Employee employee) {
    	entityManager.persist(employee);
    }
   
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
    	Employee employee = findById(id);
    	entityManager.remove(employee);
    }

}
