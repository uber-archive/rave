package fixtures;

import android.support.annotation.NonNull;
import com.ubercab.rave.BaseValidator;
import com.ubercab.rave.ExclusionStrategy;
import com.ubercab.rave.InvalidModelException;
import com.ubercab.rave.RaveError;
import fixtures.test1.SimpleCase;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.util.List;

public final class SampleFactory_Generated_Validator extends BaseValidator {
    SampleFactory_Generated_Validator() {
        addSupportedClass(SimpleCase.class);
        addSupportedClass(SimpleCase.SomeInnerClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz, @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(SimpleCase.class)) {
            validateAs((SimpleCase) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(SimpleCase.SomeInnerClass.class)) {
            validateAs((SimpleCase.SomeInnerClass) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(SimpleCase object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getNotNullField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getNotNullField(), false, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getCanBeNullField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getCanBeNullField(), true, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getBetweenOneAndFive", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getBetweenOneAndFive(), true, 1L, 5L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getNames", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNames(), true, 5L, 5L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getArrayNames", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getArrayNames(), true, 1L, 5L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getIsFalse", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, mustBeFalse(object.getIsFalse(), context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getIsTrue", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, mustBeTrue(object.getIsTrue(), context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getUberPoolState", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getUberPoolState(), "Matched", "Matching", "NotMatched"));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(SimpleCase.SomeInnerClass object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.SomeInnerClass.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.SomeInnerClass.class, "getString", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getString(), false, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}