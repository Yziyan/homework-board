package run.hxtia.workbd.common.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * 后端自定义校验的注解【验证那种只能是0、1、2的数字】
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ZOTNumber.BoolNumberValidator.class)
public @interface ZOTNumber {

    // 默认消息
    String message() default "数字只能是0、1、2";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class BoolNumberValidator implements ConstraintValidator<ZOTNumber, Short> {
        @Override
        public boolean isValid(Short num, ConstraintValidatorContext constraintValidatorContext) {
            return num == null || num == 0 || num == 1 || num == 2;
        }
    }
}