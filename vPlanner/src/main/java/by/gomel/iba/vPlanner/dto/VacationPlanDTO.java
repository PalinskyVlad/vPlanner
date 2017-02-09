package by.gomel.iba.vPlanner.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VacationPlanDTO {
	
	private long id;
	
	private String start;
	
	private String end;
	
	private String title;
	
	private String description;
	
	private long employeeId;
	
	private boolean editable;
	
	private String color;

	public VacationPlanDTO() {
		super();
	}

	public VacationPlanDTO(long id, String start, String end, String title, String description, long employeeId,
			boolean editable, String color) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.title = title;
		this.description = description;
		this.employeeId = employeeId;
		this.editable = editable;
		this.color = color;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
