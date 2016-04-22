package fixtures;

import android.support.annotation.NonNull;
import com.ubercab.rave.BaseValidator;
import com.ubercab.rave.ExclusionStrategy;
import com.ubercab.rave.InvalidModelException;
import com.ubercab.rave.RaveError;
import fixtures.floatrange.FloatRangeTestClass;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.util.List;

public final class SampleFactory_Generated_Validator extends BaseValidator {
    SampleFactory_Generated_Validator() {
        addSupportedClass(FloatRangeTestClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz, @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(FloatRangeTestClass.class)) {
            validateAs((FloatRangeTestClass) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(FloatRangeTestClass object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(FloatRangeTestClass.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble1", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble1(), -5.5D, 10.5D));
        }
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble2", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble2(), -5.0D, Double.POSITIVE_INFINITY));
        }
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble3", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble3(), Double.NEGATIVE_INFINITY, 10.0D));
        }
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble4", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble4(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        }
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble5", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble5(), -5.5D, 10.5D));
        }
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble6", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble6(), -5.0D, Double.POSITIVE_INFINITY));
        }
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble7", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble7(), Double.NEGATIVE_INFINITY, 10.0D));
        }
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble8", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble8(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        }
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestClass.class, "getDouble9", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble9(), 4.9E-324D, 1.7976931348623157E308D));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}