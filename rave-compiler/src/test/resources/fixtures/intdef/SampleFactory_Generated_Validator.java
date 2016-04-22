package fixtures;

import android.support.annotation.NonNull;
import com.ubercab.rave.BaseValidator;
import com.ubercab.rave.ExclusionStrategy;
import com.ubercab.rave.InvalidModelException;
import com.ubercab.rave.RaveError;
import fixtures.intdef.IntDefTestClass;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.util.List;

public final class SampleFactory_Generated_Validator extends BaseValidator {
    SampleFactory_Generated_Validator() {
        addSupportedClass(IntDefTestClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz, @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(IntDefTestClass.class)) {
            validateAs((IntDefTestClass) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(IntDefTestClass object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(IntDefTestClass.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(IntDefTestClass.class, "getStandard", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkIntDef(context, object.getStandard(), false, 0L, 1L, 2L, 9223372036854775807L, -9223372036854775808L));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}