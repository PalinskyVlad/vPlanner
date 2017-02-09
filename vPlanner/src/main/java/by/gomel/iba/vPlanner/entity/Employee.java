package by.gomel.iba.vPlanner.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee extends User {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Manager manager;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "employee")
	private Set<Vacation> vacations;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "employee")
	private Set<VacationPlan> vacationPlans;

	public Employee() {
		super();
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Set<Vacation> getVacations() {
		return vacations;
	}

	public void setVacations(Set<Vacation> vacations) {
		this.vacations = vacations;
	}

	public Set<VacationPlan> getVacationPlans() {
		return vacationPlans;
	}

	public void setVacationPlans(Set<VacationPlan> vacationPlans) {
		this.vacationPlans = vacationPlans;
	}

}