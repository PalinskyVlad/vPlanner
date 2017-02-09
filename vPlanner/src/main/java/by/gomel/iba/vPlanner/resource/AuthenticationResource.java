package by.gomel.iba.vPlanner.resource;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWTSigner;

import by.gomel.iba.vPlanner.dto.CredentialsDTO;
import by.gomel.iba.vPlanner.dto.UserDTO;
import by.gomel.iba.vPlanner.mapper.UserMapper;
import by.gomel.iba.vPlanner.service.UserService;

@Path("/authentication")
@PermitAll
public class AuthenticationResource {

	private final static String ISSUER = "http://localhost7001/vPlanner";

	private final static String SECRET = "vPlanner";

	@EJB
	private UserService userService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(CredentialsDTO credentialsDTO) {

		try {
			// Authenticate the user using the credentials
			UserDTO userDTO = UserMapper.INSTANCE
					.userToUserDTO(userService.authentiacte(credentialsDTO.getUserId(), credentialsDTO.getPassword()));
			if (userDTO != null) {
				String token = issueToken(userDTO.getRole());
				// Return the user and token on the response
				return Response.ok().entity(userDTO).header(HttpHeaders.AUTHORIZATION, token).build();
			}

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();

	}

	private String issueToken(String role) throws UnsupportedEncodingException {

		final long iat = System.currentTimeMillis() / 1000L; // issued at claim
		final long exp = iat + 300L; // expires claim. In this case the token
										// expires in 300 seconds

		final JWTSigner signer = new JWTSigner(SECRET);
		final HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("iss", ISSUER);
		claims.put("exp", exp);
		claims.put("iat", iat);
		claims.put("role", role);

		return signer.sign(claims);
	}

}
