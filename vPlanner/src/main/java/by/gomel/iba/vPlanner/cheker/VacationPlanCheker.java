package by.gomel.iba.vPlanner.cheker;

import java.util.Date;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.VacationPlan;
import by.gomel.iba.vPlanner.service.MailService;
import by.gomel.iba.vPlanner.service.VacationPlanService;
import by.gomel.iba.vPlanner.service.VacationService;

@Singleton
public class VacationPlanCheker {
	
	private static final String NEWLINE = "\n\n";

	private static final String GREETING = "Good afternoon. ";

	private static final String REQUIEST_ONE_MONTH_BEFORE = "A month left before the start of vacation.\n\nPlease ask your customer for get approval of planned vacation.";

	private static final String SUBJECT_VACATION_PLAN = "Vacation plan";

	@EJB
	private MailService mailService;

	@EJB
	private VacationService vacationService;

	@EJB
	private VacationPlanService vacationPlanService;

	@Schedule(hour="4", minute="0", second="0")
	public void sendEmailsAndCreateVacations() {
		System.out.println("cheker " + new Date());
		Set<VacationPlan> vacationPlans = vacationPlanService.oneMonthBeforeVacation();
		StringBuilder messageText = new StringBuilder();

		for (VacationPlan vacationPlan : vacationPlans) {
			vacationService.createVacation(vacationPlan, vacationPlan.getEmployee());
			
			messageText.setLength(0);
			messageText.append(GREETING).append(NEWLINE).append(REQUIEST_ONE_MONTH_BEFORE);
			try {
				mailService.sendEmail(vacationPlan.getEmployee().getEmail(), SUBJECT_VACATION_PLAN,
						messageText.toString());
			} catch (NamingException | MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
}
