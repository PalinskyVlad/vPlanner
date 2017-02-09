package by.gomel.iba.vPlanner.validator;

import java.util.Set;

import javax.ejb.EJB;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import by.gomel.iba.vPlanner.dto.VacationDTO;
import by.gomel.iba.vPlanner.service.VacationService;
import by.gomel.iba.vPlanner.validator.annotation.VacationDTOsValid;

public class VacationDTOsValidator implements ConstraintValidator<VacationDTOsValid, Set<VacationDTO>>{
	
	private final static long NUMBER_OF_VACATION_PARTS = 2;
	
	@Override
	public void initialize(VacationDTOsValid arg0) {

	}

	@Override
	public boolean isValid(Set<VacationDTO> vacationDTOs, ConstraintValidatorContext arg1) {
		if (vacationDTOs.size() > NUMBER_OF_VACATION_PARTS || vacationDTOs.size() == 0) {
			return false;
		}
		for(VacationDTO vacationDTO : vacationDTOs) {
			if (vacationDTO.getStart() == null || vacationDTO.getEnd() == null) {
				return false;
			}
		}
		return true;	
	}

}
