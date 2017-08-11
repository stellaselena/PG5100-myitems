package validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * Created by Stella on 11.08.2017.
 */
@NotNull
@Constraint(validatedBy = ItemValidator.class)
@Target({  //tells on what the @ annotation can be used
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE}
)
@Retention(RetentionPolicy.RUNTIME) //specify it should end up in the bytecode and be readable using reflection
@Documented //should be part of the JavaDoc of where it is applied to
public @interface ItemType {

    String message() default "Invalid type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
