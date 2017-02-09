package by.gomel.iba.vPlanner.resource;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import by.gomel.iba.vPlanner.dto.VacationPlanDTO;
import by.gomel.iba.vPlanner.mapper.VacationPlanMapper;
import by.gomel.iba.vPlanner.service.MailService;
import by.gomel.iba.vPlanner.service.VacationPlanService;
import by.gomel.iba.vPlanner.validator.annotation.VacationPlanDTOsValid;

@Path("/vacationPlans")
public class VacationPlanResource {

	@EJB
	private VacationPlanService vacationPlanService;

	@EJB
	private MailService mailService;

	@GET
	@Path("/employee/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<VacationPlanDTO> getVacationsByEmployeeId(@PathParam("employeeId") long employeeId) {
		return VacationPlanMapper.INSTANCE
				.vacationPlansToVacationPlanDTOs(vacationPlanService.findVacationPlansByEmployeeId(employeeId));
	}

	@GET
	@Path("/manager/{managerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<VacationPlanDTO> getVacationsByManagerId(@PathParam("managerId") long managerId) {
		return VacationPlanMapper.INSTANCE
				.vacationPlansToVacationPlanDTOs(vacationPlanService.findVacationPlansByManagerId(managerId));
	}

	@POST
	@Path("/employee/{employeeId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addVacationPlans(@PathParam("employeeId") long employeeId,
			@VacationPlanDTOsValid Set<VacationPlanDTO> vacationPlanDTOs) {
		try {
			mailService.sendEmailToManagerAskingToApprovePlannedVacation(employeeId);
			vacationPlanService.addVacationPlans(
					VacationPlanMapper.INSTANCE.vacationPlanDTOsToVacationPlans(vacationPlanDTOs), employeeId);
			return Response.ok().build();
		} catch (MessagingException | NamingException e) {
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	@PUT
	@Path("/approve/{employeeId}")
	public Response approveVacationPlans(@PathParam("employeeId") long employeeId) {
		try {
			mailService.sendEmailToEmployeeWithApprove(employeeId);
			vacationPlanService.approveVacationPlans(employeeId);
			return Response.ok().build();
		} catch (MessagingException | NamingException e) {
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	@PUT
	@Path("/reject/{employeeId}")
	public Response rejectVacationPlans(@PathParam("employeeId") long employeeId) {
		try {
			mailService.sendEmailToEmployeeWithReject(employeeId);
			vacationPlanService.rejectVacationPlans(employeeId);
			return Response.ok().build();
		} catch (MessagingException | NamingException e) {
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

}
