package fixtures;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.ExclusionStrategy;
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
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz, @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(InheritFrom.class)) {
            validateAs((InheritFrom) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(ValidateByInterface.class)) {
            validateAs((ValidateByInterface) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(ValidateBySecondInterface.class)) {
            validateAs((ValidateBySecondInterface) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(ValidateSample.class)) {
            validateAs((ValidateSample) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(ValidateSample2.class)) {
            validateAs((ValidateSample2) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(InheritFrom object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(InheritFrom.class);
        List<RaveError> raveErrors = null;
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateSample2.class, object, exclusionStrategy));
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateByInterface.class, object, exclusionStrategy));
        if (!setContextAndCheckshouldIgnoreMethod(InheritFrom.class, "getNonNullString", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getNonNullString(), false, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(InheritFrom.class, "getANullableString", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getANullableString(), true, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateByInterface object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateByInterface.class);
        List<RaveError> raveErrors = null;
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateBySecondInterface.class, object, exclusionStrategy));
        if (!setContextAndCheckshouldIgnoreMethod(ValidateByInterface.class, "getNonNullString", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNonNullString(), false, 1L, 2L, 1L, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateBySecondInterface object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateBySecondInterface.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(ValidateBySecondInterface.class, "getANullableString", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getANullableString(), true, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateSample object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateSample.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(ValidateSample.class, "getNotNullField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getNotNullField(), false, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(ValidateSample.class, "getCanBeNullField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getCanBeNullField(), true, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(ValidateSample.class, "getBetweenOneAndFive", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getBetweenOneAndFive(), true, 1L, 5L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(ValidateSample.class, "getNames", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNames(), true, 1L, 5L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(ValidateSample.class, "getIsFalse", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, mustBeFalse(object.getIsFalse(), context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateSample2 object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateSample2.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(ValidateSample2.class, "getNotNullField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNotNullField(), true, 1L, 5L, 1L, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
