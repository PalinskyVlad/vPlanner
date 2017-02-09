package by.gomel.iba.vPlanner.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class AuthenticationSecurityContext implements SecurityContext {

	private String role;

	public AuthenticationSecurityContext(String role) {
		this.role = role;
	}

	@Override
	public Principal getUserPrincipal() {
		return new Principal() {
			@Override
			public String getName() {
				return role;
			}
		};
	}

	// Checks if the role is the same
	@Override
	public boolean isUserInRole(String role) {
		if (this.role.equals(role))
			return true;
		return false;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public String getAuthenticationScheme() {
		return null;
	}
}