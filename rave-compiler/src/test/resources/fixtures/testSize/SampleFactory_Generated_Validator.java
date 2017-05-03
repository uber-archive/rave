package fixtures;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.testSize.SimpleCase;
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
        context.setValidatedItemName("checkDefaultSize1()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize1(), true, 10L, 10L, 1L, context));
        context.setValidatedItemName("checkDefaultSize2()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize2(), true, 1L, 15L, 1L, context));
        context.setValidatedItemName("checkDefaultSize3()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize3(), true, -9223372036854775808L, 9223372036854775807L, 2L, context));
        context.setValidatedItemName("checkDefaultSize4()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize4(), true, 10L, 10L, 1L, context));
        context.setValidatedItemName("checkDefaultSize5()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.checkDefaultSize5(), true, 1L, 20L, 5L, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
