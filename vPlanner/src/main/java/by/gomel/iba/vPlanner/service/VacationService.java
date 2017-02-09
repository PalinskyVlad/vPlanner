package by.gomel.iba.vPlanner.service;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Customer;
import by.gomel.iba.vPlanner.entity.Employee;
import by.gomel.iba.vPlanner.entity.Manager;
import by.gomel.iba.vPlanner.entity.Vacation;
import by.gomel.iba.vPlanner.entity.VacationPlan;

@Stateless
public class VacationService {

	@PersistenceContext(unitName = "PersistenceUnit")
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Vacation findById(Long id) {
		return entityManager.find(Vacation.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Set<Vacation> findVacationsByEmployeeId(long employeeId) {
		return entityManager.find(Employee.class, employeeId).getVacations();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Set<Vacation> findVacationsForManagerApprove(long employeeId) {
		return new HashSet<Vacation>(entityManager.createNamedQuery("vacation.forCustomerApprove", Vacation.class)
				.setParameter("employeeId", employeeId).getResultList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addVacations(Set<Vacation> vacations, long employeeId) {
		Employee employee = entityManager.find(Employee.class, employeeId);
		for (Vacation vacation : vacations) {
			vacation.setEmployee(employee);
			entityManager.merge(vacation);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createVacation(VacationPlan vacationPlan, Employee employee) {
		Vacation vacation = new Vacation();

		vacation.setEmployee(employee);
		vacation.setEnd(vacationPlan.getEnd());
		vacation.setStart(vacationPlan.getStart());
		vacation.setApproved(false);

		entityManager.persist(vacation);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerCustomerApprove(long vacationId, Customer customer) {
		Vacation vacation = entityManager.find(Vacation.class, vacationId);
		
		entityManager.persist(customer);
		
		vacation.setCustomer(customer);
		vacation.setApproved(true);
		
		entityManager.merge(vacation);
	}
	
	public boolean vacationIsApproved(long vacationId) {
		System.out.println("call");
		Vacation vacation = entityManager.find(Vacation.class, vacationId);
		
		if (vacation == null) {
			return false;
		}
		
		return vacation.isApproved();
	}
}
