package fixtures;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.ExclusionStrategy;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.unannotated.UnannotatedField;
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
        addSupportedClass(UnannotatedField.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz,
            @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(UnannotatedField.class)) {
            validateAs((UnannotatedField) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(UnannotatedField object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(UnannotatedField.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(UnannotatedField.class, "getNullableField", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getNullableField(), true, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
