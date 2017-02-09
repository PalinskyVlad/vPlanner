package by.gomel.iba.vPlanner.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager extends User {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "manager")
	private Set<Employee> employees;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "manager")
	private Set<VacationPlan> vacationPlans;

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Set<VacationPlan> getVacationPlans() {
		return vacationPlans;
	}

	public void setVacationPlans(Set<VacationPlan> vacationPlans) {
		this.vacationPlans = vacationPlans;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}