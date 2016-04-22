package fixtures;

import android.support.annotation.NonNull;
import com.ubercab.rave.BaseValidator;
import com.ubercab.rave.ExclusionStrategy;
import com.ubercab.rave.InvalidModelException;
import com.ubercab.rave.RaveError;
import fixtures.intrange.IntRangeTestClass;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.util.List;

public final class SampleFactory_Generated_Validator extends BaseValidator {
    SampleFactory_Generated_Validator() {
        addSupportedClass(IntRangeTestClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz, @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(IntRangeTestClass.class)) {
            validateAs((IntRangeTestClass) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(IntRangeTestClass object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(IntRangeTestClass.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(IntRangeTestClass.class, "getInt1", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getInt1(), -5L, 10L));
        }
        if (!setContextAndCheckshouldIgnoreMethod(IntRangeTestClass.class, "getInt2", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getInt2(), -5L, 9223372036854775807L));
        }
        if (!setContextAndCheckshouldIgnoreMethod(IntRangeTestClass.class, "getInt3", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getInt3(), -9223372036854775808L, 10L));
        }
        if (!setContextAndCheckshouldIgnoreMethod(IntRangeTestClass.class, "getInt4", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getInt4(), -9223372036854775808L, 9223372036854775807L));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}