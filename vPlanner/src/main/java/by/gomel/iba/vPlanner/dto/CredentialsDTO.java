package by.gomel.iba.vPlanner.dto;

public class CredentialsDTO {
	
	private String userId;
	
	private String password;

	public CredentialsDTO(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}

	public CredentialsDTO() {
		super();
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

}
