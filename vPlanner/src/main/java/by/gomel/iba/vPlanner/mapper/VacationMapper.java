package by.gomel.iba.vPlanner.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.gomel.iba.vPlanner.dto.VacationDTO;
import by.gomel.iba.vPlanner.entity.Vacation;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MappingContext;

public enum VacationMapper {

	INSTANCE;

	private final MapperFacade mapperFacade;
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String ORANGE = "#f0ad4e";
	
	private static final String GREEN = "#5cb85c";
	
	private static final String VACATION_IS_APPROVED = "Customer response is registered";
	
	private static final String VACATION_IS_NOT_APPROVED = "Please, request your customer to approve vacation";

	private VacationMapper() {
		BaseMapper.MAPPER_FACTORY.classMap(VacationDTO.class, Vacation.class)
				.customize(new CustomMapper<VacationDTO, Vacation>() {
					@Override
					public void mapAtoB(VacationDTO a, Vacation b, MappingContext context) {
						b.setId(a.getId());
						try {
							b.setStart(formatter.parse(a.getStart()));
							b.setEnd(formatter.parse(a.getEnd()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						b.setApproved(!a.isEditable());
					}

					@Override
					public void mapBtoA(Vacation b, VacationDTO a, MappingContext context) {
						a.setId(b.getId());
						a.setStart(b.getStart().toString());
						a.setEnd(b.getEnd().toString());
						a.setEditable(!b.isApproved());
						
						if (b.isApproved()) {
							a.setColor(GREEN);							
							a.setTitle(VACATION_IS_APPROVED);
						} else {
							a.setColor(ORANGE);
							a.setTitle(VACATION_IS_NOT_APPROVED);
						}
					}
				}).register();
		mapperFacade = BaseMapper.MAPPER_FACTORY.getMapperFacade();
	}

	public Vacation vacationDTOToVacation(VacationDTO vacationDTO) {
		return this.mapperFacade.map(vacationDTO, Vacation.class);
	}

	public VacationDTO vacationToVacationDTO(Vacation vacation) {
		return this.mapperFacade.map(vacation, VacationDTO.class);
	}
	
	public Set<Vacation> vacationDTOsToVacations(Set<VacationDTO> vacationDTOs) {
		if (vacationDTOs == null) {
			return null;
		}
		
		Set<Vacation> vacations = new HashSet<Vacation>();
		
		for (VacationDTO vacationDTO : vacationDTOs) {
			vacations.add(vacationDTOToVacation(vacationDTO));
		}
		return vacations;
	}

	public Set<VacationDTO> vacationsToVacationDTOs(Set<Vacation> vacations) {
		if (vacations == null) {
			return null;
		}
		
		Set<VacationDTO> vacationDTOs = new HashSet<VacationDTO>();
		
		for (Vacation vacation : vacations) {
			vacationDTOs.add(vacationToVacationDTO(vacation));
		}
		return vacationDTOs;
	}
}
