package fixtures;

import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.test3.InheritFrom;
import fixtures.test3.ValidateByInterface;
import fixtures.test3.ValidateBySecondInterface;
import fixtures.test3.ValidateSample;
import fixtures.test3.ValidateSample2;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;

@Generated(
        value = "com.uber.rave.compiler.RaveProcessor",
        comments = "https://github.com/uber-common/rave"
)
public final class SampleFactory_Generated_Validator extends BaseValidator {
    SampleFactory_Generated_Validator() {
        addSupportedClass(InheritFrom.class);
        addSupportedClass(ValidateByInterface.class);
        addSupportedClass(ValidateBySecondInterface.class);
        addSupportedClass(ValidateSample.class);
        addSupportedClass(ValidateSample2.class);
        registerSelf();
    }

    @Override
    protected void validateAs(Object object, Class<?> clazz) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(InheritFrom.class)) {
            validateAs((InheritFrom) object);
            return;
        }
        if (clazz.equals(ValidateByInterface.class)) {
            validateAs((ValidateByInterface) object);
            return;
        }
        if (clazz.equals(ValidateBySecondInterface.class)) {
            validateAs((ValidateBySecondInterface) object);
            return;
        }
        if (clazz.equals(ValidateSample.class)) {
            validateAs((ValidateSample) object);
            return;
        }
        if (clazz.equals(ValidateSample2.class)) {
            validateAs((ValidateSample2) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(InheritFrom object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(InheritFrom.class);
        List<RaveError> raveErrors = null;
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateSample2.class, object));
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateByInterface.class, object));
        context.setValidatedItemName("getNonNullString()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getNonNullString(), false, context));
        context.setValidatedItemName("getANullableString()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getANullableString(), true, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateByInterface object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateByInterface.class);
        List<RaveError> raveErrors = null;
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateBySecondInterface.class, object));
        context.setValidatedItemName("getNonNullString()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNonNullString(), false, 1L, 2L, 1L, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateBySecondInterface object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateBySecondInterface.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getANullableString()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getANullableString(), true, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateSample object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateSample.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNotNullField()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getNotNullField(), false, context));
        context.setValidatedItemName("getCanBeNullField()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getCanBeNullField(), true, context));
        context.setValidatedItemName("getBetweenOneAndFive()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getBetweenOneAndFive(), true, 1L, 5L, 1L, context));
        context.setValidatedItemName("getNames()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNames(), true, 1L, 5L, 1L, context));
        context.setValidatedItemName("getIsFalse()");
        raveErrors = mergeErrors(raveErrors, mustBeFalse(object.getIsFalse(), context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateSample2 object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateSample2.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNotNullField()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNotNullField(), true, 1L, 5L, 1L, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    public static void validateInternalFor_fixtures_test3_ValidateSample(String notNullField,
            String canBeNullField, String betweenOneAndFive, List<String> names) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateSample.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("notNullField");
        raveErrors = mergeErrors(raveErrors, checkNullable(notNullField, true, context));
        context.setValidatedItemName("canBeNullField");
        raveErrors = mergeErrors(raveErrors, checkNullable(canBeNullField, true, context));
        context.setValidatedItemName("betweenOneAndFive");
        raveErrors = mergeErrors(raveErrors, checkNullable(betweenOneAndFive, true, context));
        context.setValidatedItemName("names");
        raveErrors = mergeErrors(raveErrors, checkNullable(names, true, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    public static void validateInternalFor_fixtures_test3_ValidateSample2(String notNullField) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateSample2.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("notNullField");
        raveErrors = mergeErrors(raveErrors, checkNullable(notNullField, true, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
