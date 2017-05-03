package fixtures;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.test4.InheritFrom;
import fixtures.test4.ValidateByInterface;
import fixtures.test4.ValidateSample2;
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
        addSupportedClass(ValidateSample2.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz) throws
            InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(InheritFrom.class)) {
            validateAs((InheritFrom) object);
            return;
        }
        if (clazz.equals(ValidateByInterface.class)) {
            validateAs((ValidateByInterface) object);
            return;
        }
        if (clazz.equals(ValidateSample2.class)) {
            validateAs((ValidateSample2) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(InheritFrom object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(InheritFrom.class);
        List<RaveError> raveErrors = null;
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateSample2.class, object));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateByInterface object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateByInterface.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNonNullString()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNonNullString(), false, 1L, 2L, 1L, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateSample2 object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateSample2.class);
        List<RaveError> raveErrors = null;
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateByInterface.class, object));
        context.setValidatedItemName("getNonNullString()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNonNullString(), true, 1L, 5L, 1L, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}