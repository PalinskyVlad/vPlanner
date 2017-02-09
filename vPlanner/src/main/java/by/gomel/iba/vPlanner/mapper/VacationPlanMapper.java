package by.gomel.iba.vPlanner.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import by.gomel.iba.vPlanner.dto.VacationPlanDTO;
import by.gomel.iba.vPlanner.entity.VacationPlan;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MappingContext;

public enum VacationPlanMapper {

	INSTANCE;

	private final MapperFacade mapperFacade;
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String ORANGE = "#f0ad4e";
	
	private static final String GREEN = "#5cb85c";
	
	private static final String RED = "#d9534f";
	
	private static final String VACATION_PLAN_IS_APPROVED = "Vacation plan is approved by manager";
	
	private static final String VACATION_PLAN_IS_REJECT = "Vacation plan is reject, please change it";
	
	private static final String VACATION_PLAN_IS_AWAITING = "Vacation plan sent to the manager";

	private VacationPlanMapper() {
		BaseMapper.MAPPER_FACTORY.classMap(VacationPlanDTO.class, VacationPlan.class)
				.customize(new CustomMapper<VacationPlanDTO, VacationPlan>() {
					@Override
					public void mapAtoB(VacationPlanDTO a, VacationPlan b, MappingContext context) {
						if (a.getId() > 0) {
							b.setId(a.getId());
						}
						
						try {
							b.setStart(formatter.parse(a.getStart()));
							b.setEnd(formatter.parse(a.getEnd()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						b.setApproved(false);
						b.setEditable(false);
						
						b.setDescription(a.getDescription());
					}

					@Override
					public void mapBtoA(VacationPlan b, VacationPlanDTO a, MappingContext context) {
						a.setId(b.getId());
						a.setStart(b.getStart().toString());
						a.setEnd(b.getEnd().toString());
						a.setEditable(b.isEditable());
						
						if (!b.isApproved()) {
							if (!b.isEditable()) {
								a.setColor(ORANGE);
								a.setTitle(VACATION_PLAN_IS_AWAITING);
							} else {
								a.setColor(RED);
								a.setTitle(VACATION_PLAN_IS_REJECT);
							}
						} else {
							a.setColor(GREEN);
							a.setTitle(VACATION_PLAN_IS_APPROVED);
						}
						a.setDescription(b.getDescription());
						a.setEmployeeId(b.getEmployee().getId());
					}
				}).register();
		mapperFacade = BaseMapper.MAPPER_FACTORY.getMapperFacade();
	}

	public VacationPlan vacationPlanDTOToVacationPlan(VacationPlanDTO vacationPlanDTO) {
		return this.mapperFacade.map(vacationPlanDTO, VacationPlan.class);
	}

	public VacationPlanDTO vacationPlanToVacationPlanDTO(VacationPlan vacationPlan) {
		return this.mapperFacade.map(vacationPlan, VacationPlanDTO.class);
	}
	
	public Set<VacationPlan> vacationPlanDTOsToVacationPlans(Set<VacationPlanDTO> vacationPlanDTOs) {
		if (vacationPlanDTOs == null) {
			return null;
		}
		
		Set<VacationPlan> vacationPlans = new HashSet<VacationPlan>();
		
		for (VacationPlanDTO vacationPlanDTO : vacationPlanDTOs) {
			vacationPlans.add(vacationPlanDTOToVacationPlan(vacationPlanDTO));
		}
		return vacationPlans;
	}

	public Set<VacationPlanDTO> vacationPlansToVacationPlanDTOs(Set<VacationPlan> vacationPlans) {
		if (vacationPlans == null) {
			return null;
		}
		
		Set<VacationPlanDTO> vacationPlanDTOs = new HashSet<VacationPlanDTO>();
		
		for (VacationPlan vacationPlan : vacationPlans) {
			vacationPlanDTOs.add(vacationPlanToVacationPlanDTO(vacationPlan));
		}
		return vacationPlanDTOs;
	}
}
