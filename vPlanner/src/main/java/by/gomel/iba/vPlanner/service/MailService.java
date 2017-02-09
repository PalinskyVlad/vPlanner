package by.gomel.iba.vPlanner.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.spi.CalendarDataProvider;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.gomel.iba.vPlanner.entity.Employee;
import by.gomel.iba.vPlanner.entity.Manager;
import by.gomel.iba.vPlanner.entity.VacationPlan;

@Stateless
public class MailService {

	private static final String SPACE = " ";

	private static final String NEWLINE = "\n\n";

	private static final String GREETING = "Good afternoon, ";

	private static final String ACTION = " asks you to approve his vacation.";

	private static final String REQUEST = "Please check his statement in system, and then approves or rejects vacation plan.";

	private static final String SUBJECT_VACATION_PLAN = "Vacation plan";
	
	private static final String VACATION_APPROVED = "Your vacation was approved.";
	
	private static final String VACATION_REJECTED = "Your vacation was rejected.";
	
	private static final String REQUEST_TO_EMPLOYEE_REJECTED = "Please, change your vacation in system and try again.";

	private static final String REQUEST_TO_EMPLOYEE_APPROVED = "A month before vacation, system will sent message to you.";
	
	@PersistenceContext(unitName = "PersistenceUnit")
	private EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendEmailToManagerAskingToApprovePlannedVacation(long employeeId)
			throws AddressException, MessagingException, NamingException {
		Employee employee = entityManager.find(Employee.class, employeeId);
		Manager manager = employee.getManager();

		StringBuilder messageText = new StringBuilder();

		messageText.append(GREETING).append(manager.getFirstName()).append(NEWLINE).append(employee.getFirstName())
				.append(SPACE).append(employee.getFirstName()).append(ACTION).append(NEWLINE).append(REQUEST);

		sendEmail(manager.getEmail(), SUBJECT_VACATION_PLAN, messageText.toString());
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendEmailToEmployeeWithReject(long employeeId)
			throws AddressException, MessagingException, NamingException {
		Employee employee = entityManager.find(Employee.class, employeeId);
		
		StringBuilder messageText = new StringBuilder();

		messageText.append(GREETING).append(employee.getFirstName()).append(NEWLINE)
				.append(VACATION_REJECTED).append(NEWLINE).append(REQUEST_TO_EMPLOYEE_REJECTED);

		sendEmail(employee.getEmail(), SUBJECT_VACATION_PLAN, messageText.toString());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendEmailToEmployeeWithApprove(long employeeId)
			throws AddressException, MessagingException, NamingException {
		Employee employee = entityManager.find(Employee.class, employeeId);
		
		StringBuilder messageText = new StringBuilder();

		messageText.append(GREETING).append(employee.getFirstName()).append(NEWLINE)
				.append(VACATION_APPROVED).append(NEWLINE).append(REQUEST_TO_EMPLOYEE_APPROVED);

		sendEmail(employee.getEmail(), SUBJECT_VACATION_PLAN, messageText.toString());
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendEmail(String to, String subject, String messageText)
			throws NamingException, AddressException, MessagingException {
		InitialContext ic = new InitialContext();
		Session session = (Session) ic.lookup("vPlannerMailSession");

		Message msg = new MimeMessage(session);
		msg.setFrom();
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
		msg.setSubject(SUBJECT_VACATION_PLAN);
		msg.setSentDate(new Date());

		// Content is stored in a MIME multi-part message
		// with one body part
		MimeBodyPart mbp = new MimeBodyPart();
		mbp.setText(messageText.toString());
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp);
		msg.setContent(mp);
		Transport.send(msg);
	}
}
