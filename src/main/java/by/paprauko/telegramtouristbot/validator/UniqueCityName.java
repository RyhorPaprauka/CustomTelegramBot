package by.paprauko.telegramtouristbot.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotBlank
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCityNameValidator.class)
public @interface UniqueCityName {

    String message() default "There is already city with this name!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
