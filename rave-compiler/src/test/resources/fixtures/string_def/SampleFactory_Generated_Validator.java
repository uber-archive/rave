package fixtures;

import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.string_def.StringDefCases;
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
        addSupportedClass(StringDefCases.class);
        registerSelf();
    }

    @Override
    protected void validateAs(Object object, Class<?> clazz) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(StringDefCases.class)) {
            validateAs((StringDefCases) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(StringDefCases object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(StringDefCases.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNonNullableString1()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullableString1(), "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("getNullableString()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(true, context, object.getNullableString(), "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("getNonNullabeString2()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullabeString2(), "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("getNonNullableArray()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullableArray(), "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("getNonNullableArray2()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullableArray2(), "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("getNullableArray()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(true, context, object.getNullableArray(), "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("getNonNullList()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullList(), "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("getNullableList()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(true, context, object.getNullableList(), "Matched", "Matching", "NotMatched"));
        context.setValidatedItemName("getNonNullableList2()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullableList2(), "Matched", "Matching", "NotMatched"));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
