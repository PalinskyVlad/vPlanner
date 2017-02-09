package by.gomel.iba.vPlanner.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Manager;

@Stateless
public class ManagerDAO extends AbstractDAO<Manager> {
	
    @PersistenceContext(unitName = "PersistenceUnit")
    private EntityManager entityManager;

	public ManagerDAO() {
		super(Manager.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}


}
