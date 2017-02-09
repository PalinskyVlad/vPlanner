package by.gomel.iba.vPlanner.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Employee;

@Stateless
public class EmployeeDAO extends AbstractDAO<Employee> {

	@PersistenceContext(unitName = "PersistenceUnit")
	private EntityManager entityManager;

	public EmployeeDAO() {
		super(Employee.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
