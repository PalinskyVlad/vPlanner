package by.gomel.iba.vPlanner.resource;

import java.util.Set;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import by.gomel.iba.vPlanner.dto.VacationDTO;
import by.gomel.iba.vPlanner.entity.Customer;
import by.gomel.iba.vPlanner.mapper.VacationMapper;
import by.gomel.iba.vPlanner.service.VacationService;
import by.gomel.iba.vPlanner.validator.annotation.CustomerValid;
import by.gomel.iba.vPlanner.validator.annotation.VacationDTOsValid;

@Path("/vacations")
public class VacationResource {

	@EJB
	private VacationService vacationService;

	@GET
	@Path("/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<VacationDTO> getVacations(@PathParam("employeeId") long employeeId) {
		return VacationMapper.INSTANCE.vacationsToVacationDTOs(vacationService.findVacationsByEmployeeId(employeeId));
	}

	@POST
	@Path("/{employeeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addVacations(@PathParam("employeeId") long employeeId,
			@VacationDTOsValid Set<VacationDTO> vacationDTOs) {
		vacationService.addVacations(VacationMapper.INSTANCE.vacationDTOsToVacations(vacationDTOs), employeeId);
	}

	@GET
	@Path("/forCustomerApprove/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<VacationDTO> getVacationForCustomerApprove(@PathParam("employeeId") long employeeId) {
		return VacationMapper.INSTANCE.vacationsToVacationDTOs(vacationService.findVacationsForManagerApprove(employeeId));
	}

	@POST
	@Path("/approve/{vacationId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void registerCustomerApprove(@PathParam("vacationId") long vacationId, @CustomerValid Customer customer) {
		vacationService.registerCustomerApprove(vacationId, customer);
	}

}
