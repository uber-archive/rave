package fixtures;

import android.support.annotation.NonNull;
import com.uber.rave.BaseValidator;
import com.uber.rave.ExclusionStrategy;
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
    protected void validateAs(@NonNull Object object, @NonNull Class<?> clazz, @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(StringDefCases.class)) {
            validateAs((StringDefCases) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(StringDefCases object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(StringDefCases.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNonNullableString1", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullableString1(), "Matched", "Matching", "NotMatched"));
        }
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNullableString", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(true, context, object.getNullableString(), "Matched", "Matching", "NotMatched"));
        }
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNonNullabeString2", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullabeString2(), "Matched", "Matching", "NotMatched"));
        }
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNonNullableArray", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullableArray(), "Matched", "Matching", "NotMatched"));
        }
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNonNullableArray2", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullableArray2(), "Matched", "Matching", "NotMatched"));
        }
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNullableArray", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(true, context, object.getNullableArray(), "Matched", "Matching", "NotMatched"));
        }
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNonNullList", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullList(), "Matched", "Matching", "NotMatched"));
        }
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNullableList", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(true, context, object.getNullableList(), "Matched", "Matching", "NotMatched"));
        }
        if (!setContextAndCheckshouldIgnoreMethod(StringDefCases.class, "getNonNullableList2", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getNonNullableList2(), "Matched", "Matching", "NotMatched"));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
