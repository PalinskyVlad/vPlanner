package by.gomel.iba.vPlanner.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Employee;
import by.gomel.iba.vPlanner.entity.Manager;
import by.gomel.iba.vPlanner.entity.VacationPlan;

@Stateless
public class VacationPlanService {

	@PersistenceContext(unitName = "PersistenceUnit")
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public VacationPlan findById(long id) {
		return entityManager.find(VacationPlan.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Set<VacationPlan> findVacationPlansByEmployeeId(long employeeId) {
		return entityManager.find(Employee.class, employeeId).getVacationPlans();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Set<VacationPlan> findVacationPlansByManagerId(long managerId) {
		return new HashSet<VacationPlan>(
				entityManager.createNamedQuery("vacationPlan.forManagerApprove", VacationPlan.class)
						.setParameter("managerId", managerId).getResultList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Set<VacationPlan> oneMonthBeforeVacation() {
		Date start = new Date();
		start.setMonth(start.getMonth() + 1);
		Date end = new Date();
		end.setMonth(end.getMonth() + 1);
		end.setDate(end.getDate() + 1);
		
		return new HashSet<VacationPlan>(
				entityManager.createNamedQuery("vacationPlan.oneMonthBeforeVacation", VacationPlan.class)
						.setParameter("start", start).setParameter("end", end).getResultList());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addVacationPlans(Set<VacationPlan> vacationPlans, long employeeId) {
		Employee employee = entityManager.find(Employee.class, employeeId);
		Manager manager = employee.getManager();
		for (VacationPlan vacationPlan : vacationPlans) {
			vacationPlan.setEmployee(employee);
			vacationPlan.setManager(manager);
			entityManager.merge(vacationPlan);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void approveVacationPlans(long employeeId) {
		entityManager.createNamedQuery("vacationPlan.approveVacationPlans").setParameter("employeeId", employeeId)
				.executeUpdate();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void rejectVacationPlans(long employeeId) {
		entityManager.createNamedQuery("vacationPlan.rejectVacationPlans").setParameter("employeeId", employeeId)
				.executeUpdate();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(Long id) {
		VacationPlan vacationPlan = findById(id);
		entityManager.remove(vacationPlan);
	}

}
