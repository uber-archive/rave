package fixtures;

import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.intrange.IntRangeTestClass;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.util.List;
import javax.annotation.Generated;

@Generated(
        value = "com.uber.rave.compiler.RaveProcessor",
        comments = "https://github.com/uber-common/rave"
)
public final class SampleFactory_Generated_Validator extends BaseValidator {
    SampleFactory_Generated_Validator() {
        addSupportedClass(IntRangeTestClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(Object object, Class<?> clazz) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(IntRangeTestClass.class)) {
            validateAs((IntRangeTestClass) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(IntRangeTestClass object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(IntRangeTestClass.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getInt1()");
        raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getInt1(), -5L, 10L));
        context.setValidatedItemName("getInt2()");
        raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getInt2(), -5L, 9223372036854775807L));
        context.setValidatedItemName("getInt3()");
        raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getInt3(), -9223372036854775808L, 10L));
        context.setValidatedItemName("getInt4()");
        raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getInt4(), -9223372036854775808L, 9223372036854775807L));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
