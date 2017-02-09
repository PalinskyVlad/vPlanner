package by.gomel.iba.vPlanner.resource;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import by.gomel.iba.vPlanner.dto.UserDTO;
import by.gomel.iba.vPlanner.mapper.UserMapper;
import by.gomel.iba.vPlanner.service.EmployeeService;

@Path("/employees")
public class EmployeeResource {
	
	@EJB
	private EmployeeService employeeService;
	
	@GET
	@Path("/{employeeId}")
	@RolesAllowed("Manager")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO getById(@PathParam("employeeId") long employeeId) {
		System.out.println(employeeId);
		System.out.println(employeeService.findById(employeeId));
		return UserMapper.INSTANCE.userToUserDTO(employeeService.findById(employeeId));
	}
	
	@GET
	@Path("/manager/{managerId}")
	@RolesAllowed("Manager")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<UserDTO> getByManagerId(@PathParam("managerId") long managerId) {
		System.out.println(UserMapper.INSTANCE.employeesToUserDTOs(employeeService.findByManagerId(managerId)));
		return UserMapper.INSTANCE.employeesToUserDTOs(employeeService.findByManagerId(managerId));
	}

}
