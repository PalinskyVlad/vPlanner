package by.gomel.iba.vPlanner.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import by.gomel.iba.vPlanner.dto.VacationPlanDTO;
import by.gomel.iba.vPlanner.service.VacationPlanService;
import by.gomel.iba.vPlanner.validator.annotation.VacationPlanDTOsValid;

public class VacationPlanDTOsValidator implements ConstraintValidator<VacationPlanDTOsValid, Set<VacationPlanDTO>> {

	private final static long NUMBER_OF_VACATION_PARTS = 2;

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void initialize(VacationPlanDTOsValid arg0) {

	}

	@Override
	public boolean isValid(Set<VacationPlanDTO> vacationPlanDTOs, ConstraintValidatorContext arg1) {

		Date dateForCheckMonth = new Date();
		
		dateForCheckMonth.setMonth(dateForCheckMonth.getMonth() + 1);

		// check number of vacation parts
		if (vacationPlanDTOs.size() > NUMBER_OF_VACATION_PARTS && vacationPlanDTOs.size() == 0) {
			return false;
		}

		// check start dates
		for (VacationPlanDTO vacationPlanDTO : vacationPlanDTOs) {
			try {
				if (dateForCheckMonth.after(formatter.parse(vacationPlanDTO.getStart()))) {
					return false;
				}
			} catch (ParseException e) {
				return false;
			}
		}
		
		if (vacationPlanDTOs.size() == NUMBER_OF_VACATION_PARTS) {
			for (VacationPlanDTO vacationPlanDTO : vacationPlanDTOs) {
				try {
					Date dateForCheckVacationLength = formatter.parse(vacationPlanDTO.getStart());
					
					dateForCheckVacationLength.setDate(dateForCheckVacationLength.getDate() + 14);
					if (dateForCheckVacationLength.before(formatter.parse(vacationPlanDTO.getEnd()))) {
						return true;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

}
