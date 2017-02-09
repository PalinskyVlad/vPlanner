package by.gomel.iba.vPlanner.resource;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/login")
public class LoginResource {
	
	@POST
	@Consumes(MediaType.TEXT_HTML)
	public Response loginPage(String jwt) {
		System.out.println("asda");
		System.out.println(jwt);
		return Response.ok("Hello update!").build();
    }

}
