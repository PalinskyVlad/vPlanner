package by.gomel.iba.vPlanner.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.VacationPlan;

@Stateless
public class VacationPlanDAO extends AbstractDAO<VacationPlan>{
	
    @PersistenceContext(unitName = "PersistenceUnit")
    private EntityManager entityManager;

	public VacationPlanDAO() {
		super(VacationPlan.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
