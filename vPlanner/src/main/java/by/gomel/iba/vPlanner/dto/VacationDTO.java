package by.gomel.iba.vPlanner.dto;

public class VacationDTO {
	
	private long id;
	
	private String start;
	
	private String end;
	
	private String title;
	
	private String description;
	
	private String color;
	
	private boolean editable;

	public VacationDTO(long id, String start, String end, String title, String description, String color, 
			boolean editable) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.title = title;
		this.color = color;
		this.editable = editable;
	}

	public VacationDTO() {
		super();
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
