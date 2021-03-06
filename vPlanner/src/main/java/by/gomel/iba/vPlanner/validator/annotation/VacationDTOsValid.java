package by.gomel.iba.vPlanner.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import by.gomel.iba.vPlanner.validator.VacationDTOsValidator;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VacationDTOsValidator.class)
public @interface VacationDTOsValid {

	String message() default "{com.example.validation.constraints.email}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
