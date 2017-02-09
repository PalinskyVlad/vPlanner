package by.gomel.iba.vPlanner.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vacation_plan")
@NamedQueries({
	@NamedQuery(name="vacationPlan.forManagerApprove", query = "SELECT v from VacationPlan v WHERE v.manager.id = :managerId and v.approved = false and v.editable = false"),
		@NamedQuery(name = "vacationPlan.approveVacationPlans", query = "UPDATE VacationPlan v SET v.approved = true WHERE v.approved = false and v.employee.id = :employeeId"),
		@NamedQuery(name = "vacationPlan.rejectVacationPlans", query = "UPDATE VacationPlan v SET v.editable = true WHERE v.approved = false and v.employee.id = :employeeId"),
		@NamedQuery(name = "vacationPlan.oneMonthBeforeVacation",query = "SELECT v from VacationPlan v WHERE v.start > :start and v.start < :end")
})		
public class VacationPlan implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;

	@Column(name = "start")
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	@Column(name = "end")
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;
	
	@Column(name = "description", length = 256)
	private String description;

	@Column(name = "approved")
	private boolean approved;
	
	@Column(name = "editable")
	private boolean editable;

	@ManyToOne
	private Manager manager;

	@ManyToOne
	private Employee employee;

	public VacationPlan() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "VacationPlan [id=" + id + ", start=" + start + ", end=" + end + ", approved="
				+ approved + ", description=" + description + "]";
	}
}
