package fixtures.validationstrategy;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.ExclusionStrategy;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.validationstrategy.simple.SimpleCase;
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
public final class StrictModeFactory_Generated_Validator extends BaseValidator {
    StrictModeFactory_Generated_Validator() {
        addSupportedClass(SimpleCase.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz,
            @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(SimpleCase.class)) {
            validateAs((SimpleCase) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(SimpleCase object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getNotNullField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getNotNullField(), false, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getCanBeNullField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getCanBeNullField(), true, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SimpleCase.class, "getUnannotatedField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getUnannotatedField(), false, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
