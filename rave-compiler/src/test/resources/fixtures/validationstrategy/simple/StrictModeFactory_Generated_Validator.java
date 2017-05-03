package fixtures.validationstrategy;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
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
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz) throws
            InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(SimpleCase.class)) {
            validateAs((SimpleCase) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(SimpleCase object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNotNullField()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getNotNullField(), false, context));
        context.setValidatedItemName("getCanBeNullField()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getCanBeNullField(), true, context));
        context.setValidatedItemName("getUnannotatedField()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getUnannotatedField(), false, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
