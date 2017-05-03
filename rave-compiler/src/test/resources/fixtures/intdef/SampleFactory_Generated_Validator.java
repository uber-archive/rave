package fixtures;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.intdef.IntDefTestClass;
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
        addSupportedClass(IntDefTestClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz) throws
            InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(IntDefTestClass.class)) {
            validateAs((IntDefTestClass) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(IntDefTestClass object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(IntDefTestClass.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getStandard()");
        raveErrors = mergeErrors(raveErrors, checkIntDef(context, object.getStandard(), false, 0L, 1L, 2L, 9223372036854775807L, -9223372036854775808L));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
