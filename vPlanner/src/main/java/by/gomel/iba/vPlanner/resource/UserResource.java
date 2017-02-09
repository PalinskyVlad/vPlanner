package by.gomel.iba.vPlanner.resource;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import by.gomel.iba.vPlanner.entity.User;
import by.gomel.iba.vPlanner.service.UserService;

@Stateless
@Path("/users")
public class UserResource {
	
	@EJB
	private UserService userService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createUser(User user) {
		System.out.println(user);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") long id) {
		return userService.findById(id);
	}
	
	@GET
	@Path("/authenticate")
	@Produces(MediaType.APPLICATION_JSON)
	public User userAuthenticate() {
		
		System.out.println("method call");
		User user = new User();
		user.setEmail("test@email.ru");
		user.setFirstName("test name");
		user.setId(1);
		user.setLastName("test last name");
		user.setPassword("password");
		user.setUserId("username");
		return user;
	}

}
