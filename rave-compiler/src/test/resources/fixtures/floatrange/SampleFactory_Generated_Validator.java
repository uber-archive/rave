
package fixtures;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.floatrange.FloatRangeTestClass;
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
        addSupportedClass(FloatRangeTestClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz) throws
            InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(FloatRangeTestClass.class)) {
            validateAs((FloatRangeTestClass) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(FloatRangeTestClass object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(FloatRangeTestClass.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getDouble1()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble1(), -5.5D, 10.5D));
        context.setValidatedItemName("getDouble2()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble2(), -5.0D, Double.POSITIVE_INFINITY));
        context.setValidatedItemName("getDouble3()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble3(), Double.NEGATIVE_INFINITY, 10.0D));
        context.setValidatedItemName("getDouble4()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble4(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        context.setValidatedItemName("getDouble5()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble5(), -5.5D, 10.5D));
        context.setValidatedItemName("getDouble6()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble6(), -5.0D, Double.POSITIVE_INFINITY));
        context.setValidatedItemName("getDouble7()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble7(), Double.NEGATIVE_INFINITY, 10.0D));
        context.setValidatedItemName("getDouble8()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble8(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        context.setValidatedItemName("getDouble9()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getDouble9(), 4.9E-324D, 1.7976931348623157E308D));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
