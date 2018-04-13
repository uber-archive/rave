package fixtures;

import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.test1.SimpleCase;
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
        addSupportedClass(SimpleCase.class);
        addSupportedClass(SimpleCase.SomeInnerClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(Object object, Class<?> clazz) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(SimpleCase.class)) {
            validateAs((SimpleCase) object);
            return;
        }
        if (clazz.equals(SimpleCase.SomeInnerClass.class)) {
            validateAs((SimpleCase.SomeInnerClass) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(SimpleCase object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNotNullField()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getNotNullField(), false, context));
        context.setValidatedItemName("getCanBeNullField()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getCanBeNullField(), true, context));
        context.setValidatedItemName("getBetweenOneAndFive()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getBetweenOneAndFive(), true, 1L, 5L, 1L, context));
        context.setValidatedItemName("getNames()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNames(), true, 5L, 5L, 1L, context));
        context.setValidatedItemName("getArrayNames()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getArrayNames(), true, 1L, 5L, 1L, context));
        context.setValidatedItemName("getIsFalse()");
        raveErrors = mergeErrors(raveErrors, mustBeFalse(object.getIsFalse(), context));
        context.setValidatedItemName("getIsTrue()");
        raveErrors = mergeErrors(raveErrors, mustBeTrue(object.getIsTrue(), context));
        context.setValidatedItemName("getUberPoolState()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getUberPoolState(), "Matched", "Matching", "NotMatched"));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(SimpleCase.SomeInnerClass object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.SomeInnerClass.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getString()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getString(), false, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
