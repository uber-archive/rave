package fixtures;

import android.support.annotation.NonNull;
import com.ubercab.rave.BaseValidator;
import com.ubercab.rave.ExclusionStrategy;
import com.ubercab.rave.InvalidModelException;
import com.ubercab.rave.RaveError;
import fixtures.testSize.SimpleCase;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.util.List;

public final class SampleFactory_Generated_Validator extends BaseValidator {
    SampleFactory_Generated_Validator() {
        addSupportedClass(SimpleCase.class);
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
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(SimpleCase object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "checkDefaultSize1", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize1(), true, 10L, 10L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "checkDefaultSize2", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize2(), true, 1L, 15L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "checkDefaultSize3", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize3(), true, -9223372036854775808L, 9223372036854775807L, 2L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "checkDefaultSize4", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize4(), true, 10L, 10L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "checkDefaultSize5", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize5(), true, 1L, 20L, 5L, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}