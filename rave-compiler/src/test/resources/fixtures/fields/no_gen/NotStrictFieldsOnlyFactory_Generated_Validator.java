package fixtures.fields;

import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.fields.no_gen.NoGen;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(
        value = "com.uber.rave.compiler.RaveProcessor",
        comments = "https://github.com/uber-common/rave"
)
public final class NotStrictFieldsOnlyFactory_Generated_Validator extends BaseValidator {
    NotStrictFieldsOnlyFactory_Generated_Validator() {
        addSupportedClass(NoGen.class);
        registerSelf();
    }

    @Override
    protected void validateAs(Object object, Class<?> clazz) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(NoGen.class)) {
            validateAs((NoGen) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(NoGen object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(NoGen.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getState()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getState(), true, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    public static void validateInternalFor_fixtures_fields_no_gen_NoGen(String notNullField,
            String canBeNullField, String betweenOneAndFive, List<String> names, Map<String, NoGen> map)
            throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(NoGen.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("notNullField");
        raveErrors = mergeErrors(raveErrors, checkNullable(notNullField, true, context));
        context.setValidatedItemName("canBeNullField");
        raveErrors = mergeErrors(raveErrors, checkNullable(canBeNullField, true, context));
        context.setValidatedItemName("betweenOneAndFive");
        raveErrors = mergeErrors(raveErrors, checkNullable(betweenOneAndFive, true, context));
        context.setValidatedItemName("names");
        raveErrors = mergeErrors(raveErrors, checkNullable(names, false, context));
        context.setValidatedItemName("map");
        raveErrors = mergeErrors(raveErrors, checkNullable(map, true, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
