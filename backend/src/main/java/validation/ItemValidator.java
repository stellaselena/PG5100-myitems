package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Stella on 11.08.2017.
 */
public class ItemValidator implements ConstraintValidator<ItemType,String> {
    @Override
    public void initialize(ItemType constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return ItemTypes.getTypes().stream()
                .anyMatch(s -> s.equalsIgnoreCase(value));
    }
}
