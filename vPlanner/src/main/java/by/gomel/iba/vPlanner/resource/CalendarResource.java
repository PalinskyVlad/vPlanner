package by.gomel.iba.vPlanner.resource;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

@Stateless
@Path("/calendar")
public class CalendarResource {

	@GET
	@Produces("text/html")
	public Response getCalendar() {
		return Response.ok("app/calendar.html").build();
	} 
	
	@GET
	@Path("/1")
	@Produces("text/html")
	public Response getCalendar1() {
		return Response.ok(new Viewable("/calendar.html")).build();
	} 
	
	@GET
	@Path("/2")
	@Produces("text/html")
	public Response getCalendar2() {
		return Response.ok("calendar.html").build();
	} 
	@GET
	@Path("/3")
	@Produces("text/html")
	public Response getCalendar3() {
		return Response.ok("asdasd").build();
	} 
}
