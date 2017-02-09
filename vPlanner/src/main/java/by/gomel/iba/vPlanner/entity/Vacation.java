package by.gomel.iba.vPlanner.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vacation")
@NamedQuery(name = "vacation.forCustomerApprove", query = "SELECT v from Vacation v WHERE v.employee.id = :employeeId and v.approved = false")
public class Vacation implements Serializable {

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

	@ManyToOne
	private Employee employee;

	@ManyToOne
	private Customer customer;

	public Vacation() {
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String toString() {
		return "Vacation [id=" + id + ", start=" + start + ", end=" + end + ", approved="
				+ approved;
	}

}
