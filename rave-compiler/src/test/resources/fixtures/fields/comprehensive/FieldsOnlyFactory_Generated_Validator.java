package fixtures.fields;

import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.fields.comprehensive.SimpleCase;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
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
public final class FieldsOnlyFactory_Generated_Validator extends BaseValidator {
    FieldsOnlyFactory_Generated_Validator() {
        addSupportedClass(SimpleCase.class);
        addSupportedClass(SimpleCase.SomeInnerClass.class);
        registerSelf();
    }

    @Override
    protected void validateAs(Object object, Class<?> clazz) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(SimpleCase.class)) {
            validateAs((SimpleCase) object);
            return;
        }
        if (clazz.equals(SimpleCase.SomeInnerClass.class)) {
            validateAs((SimpleCase.SomeInnerClass) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(SimpleCase object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getUberPoolState()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getUberPoolState(), "Matched", "Matching", "NotMatched"));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(SimpleCase.SomeInnerClass object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.SomeInnerClass.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getString()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getString(), false, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    public static void validateInternalFor_fixtures_fields_comprehensive_SimpleCase(String notNullField,
            String canBeNullField, String betweenOneAndFive, List<String> names, Integer testIntdef1,
            int testIntdef2, float testFloatDef, Map<String, SimpleCase> map) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("notNullField");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, notNullField, "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("canBeNullField");
        raveErrors = mergeErrors(raveErrors, checkNullable(canBeNullField, false, context));
        context.setValidatedItemName("betweenOneAndFive");
        raveErrors = mergeErrors(raveErrors, checkNullable(betweenOneAndFive, false, context));
        context.setValidatedItemName("names");
        raveErrors = mergeErrors(raveErrors, checkNullable(names, false, context));
        context.setValidatedItemName("testIntdef1");
        raveErrors = mergeErrors(raveErrors, checkIntDef(context, testIntdef1, false, 1, 2, 3));
        context.setValidatedItemName("testIntdef2");
        raveErrors = mergeErrors(raveErrors, checkIntDef(context, testIntdef2, false, 1, 2, 3));
        context.setValidatedItemName("testFloatDef");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, testFloatDef, 0.1D, 0.5D));
        context.setValidatedItemName("map");
        raveErrors = mergeErrors(raveErrors, checkNullable(map, false, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    public static void validateInternalFor_fixtures_fields_comprehensive_SimpleCase_SomeInnerClass(String someString)
            throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SimpleCase.SomeInnerClass.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("someString");
        raveErrors = mergeErrors(raveErrors, checkNullable(someString, false, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}