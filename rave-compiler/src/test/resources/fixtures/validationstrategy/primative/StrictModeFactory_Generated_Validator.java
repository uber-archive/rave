package fixtures.validationstrategy;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.ExclusionStrategy;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.validationstrategy.primative.PrimativeCase;
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
        addSupportedClass(PrimativeCase.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz,
            @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(PrimativeCase.class)) {
            validateAs((PrimativeCase) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(PrimativeCase object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(PrimativeCase.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(PrimativeCase.class, "getObjectField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getObjectField(), false, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
