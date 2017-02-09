package by.gomel.iba.vPlanner.resource;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import by.gomel.iba.vPlanner.service.CustomerService;

@Path("/customers")
public class CustomerResource {
	
	@EJB
	private CustomerService customerService;
	
	@GET
	@Path("/{id}")
	public Response getCustomer(@PathParam("id") long id) {
		return Response.status(200)
				.entity(customerService.findById(id).toString()).build();
	}

}
