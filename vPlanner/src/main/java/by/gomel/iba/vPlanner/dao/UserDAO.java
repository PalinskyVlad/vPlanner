package by.gomel.iba.vPlanner.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.User;

@Stateless
public class UserDAO extends AbstractDAO<User> {

	@PersistenceContext(unitName = "PersistenceUnit")
	private EntityManager entityManager;

	public UserDAO() {
		super(User.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
