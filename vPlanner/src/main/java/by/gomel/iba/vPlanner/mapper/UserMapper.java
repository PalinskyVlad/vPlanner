package by.gomel.iba.vPlanner.mapper;

import java.util.HashSet;
import java.util.Set;

import by.gomel.iba.vPlanner.dto.UserDTO;
import by.gomel.iba.vPlanner.entity.Employee;
import by.gomel.iba.vPlanner.entity.Manager;
import by.gomel.iba.vPlanner.entity.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MappingContext;

public enum UserMapper {

	INSTANCE;

	private final MapperFacade mapperFacade;

	private UserMapper() {
		BaseMapper.MAPPER_FACTORY.classMap(UserDTO.class, User.class).customize(new CustomMapper<UserDTO, User>() {
			@Override
			public void mapAtoB(UserDTO a, User b, MappingContext context) {
				b.setId(a.getId());
				b.setUserId(a.getUserId());
				b.setEmail(a.getEmail());
				b.setPassword(a.getPassword());
				b.setFirstName(a.getFirstName());
				b.setLastName(a.getLastName());
			}

			@Override
			public void mapBtoA(User b, UserDTO a, MappingContext context) {
				a.setId(b.getId());
				a.setUserId(b.getUserId());
				a.setEmail(b.getEmail());
				a.setFirstName(b.getFirstName());
				a.setLastName(b.getLastName());
				if (b instanceof Employee) {
					a.setRole("Employee");
				}
				if (b instanceof Manager) {
					a.setRole("Manager");
				}
			}
		}).register();
		mapperFacade = BaseMapper.MAPPER_FACTORY.getMapperFacade();
	}

	public User userDTOToUser(UserDTO userDTO) {
		return this.mapperFacade.map(userDTO, User.class);
	}

	public UserDTO userToUserDTO(User user) {
		return this.mapperFacade.map(user, UserDTO.class);
	}
	
	public Set<User> userDTOsToUsers(Set<UserDTO> userDTOs) {
		if (userDTOs == null) {
			return null;
		}
		
		Set<User> users = new HashSet<User>();
		
		for (UserDTO userDTO : userDTOs) {
			users.add(userDTOToUser(userDTO));
		}
		return users;
	}

	public Set<UserDTO> usersToUserDTOs(Set<User> users) {
		if (users == null) {
			return null;
		}
		
		Set<UserDTO> userDTOs = new HashSet<UserDTO>();
		
		for (User user : users) {
			userDTOs.add(userToUserDTO(user));
		}
		return userDTOs;
	}

	public Set<UserDTO> employeesToUserDTOs(Set<Employee> employees) {
		if (employees == null) {
			return null;
		}
		
		Set<UserDTO> userDTOs = new HashSet<UserDTO>();
		
		for (Employee employee : employees) {
			userDTOs.add(userToUserDTO(employee));
		}
		return userDTOs;
	}

}
