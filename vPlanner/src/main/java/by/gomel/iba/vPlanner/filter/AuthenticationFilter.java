package by.gomel.iba.vPlanner.filter;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import by.gomel.iba.vPlanner.security.AuthenticationSecurityContext;

@Priority(Priorities.AUTHORIZATION)
@PreMatching
public class AuthenticationFilter implements ContainerRequestFilter {
	
	private final static String SECRET = "vPlanner";
	
	private final static String ROLE = "role";
	
	private final static String BEARER = "Bearer";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get request headers
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Check that it isn't empty
		if (authorizationHeader == null)
			return;

		// Get token from header
		String token = authorizationHeader.substring(BEARER.length()).trim();

		try {

			// verify token
			final JWTVerifier verifier = new JWTVerifier(SECRET);
			System.out.println(token);
			// Get claims from token
			final Map<String, Object> claims = verifier.verify(token);
			// Get user role from token
			final String role = (String) claims.get(ROLE);
			System.out.println(role);

			// Set security context for check user role
			requestContext.setSecurityContext(new AuthenticationSecurityContext(role));

		} catch (JWTVerifyException | InvalidKeyException | NoSuchAlgorithmException | IllegalStateException
				| java.security.SignatureException e) {

			// if token isn't valid
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

		}
	}

}
