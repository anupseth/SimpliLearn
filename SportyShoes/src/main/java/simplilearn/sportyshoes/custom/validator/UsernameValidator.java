package simplilearn.sportyshoes.custom.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

	@Override
	public void initialize(UsernameConstraint username) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		return false;
	}

}
