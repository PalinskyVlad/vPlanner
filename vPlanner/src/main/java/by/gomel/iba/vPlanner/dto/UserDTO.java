package by.gomel.iba.vPlanner.dto;


public class UserDTO {
	
	private long id;
	
	private String role;
	
	private String userId;
	
	private String password;
	
	private String email;
	
	private String firstName;
	
	private String lastName;

	public UserDTO(long id, String role, String userId, String password, String email, String firstName, String lastName) {
		super();
		this.id = id;
		this.role = role;
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public UserDTO() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", role=" + role + ", userId=" + userId + ", password=" + password + ", email="
				+ email + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	

}
