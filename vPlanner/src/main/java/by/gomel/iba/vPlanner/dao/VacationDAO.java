package by.gomel.iba.vPlanner.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Vacation;

@Stateless
public class VacationDAO extends AbstractDAO<Vacation> {
	
    @PersistenceContext(unitName = "PersistenceUnit")
    private EntityManager entityManager;

	public VacationDAO() {
		super(Vacation.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
