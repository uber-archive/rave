package fixtures;

import com.uber.rave.BaseValidator;
import com.uber.rave.InvalidModelException;
import com.uber.rave.RaveError;
import fixtures.maps.ModelWithMap;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(
        value = "com.uber.rave.compiler.RaveProcessor",
        comments = "https://github.com/uber-common/rave"
)
public final class SampleFactory_Generated_Validator extends BaseValidator {
    SampleFactory_Generated_Validator() {
        addSupportedClass(ModelWithMap.class);
        registerSelf();
    }

    @Override
    protected void validateAs(Object object, Class<?> clazz) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(ModelWithMap.class)) {
            validateAs((ModelWithMap) object);
            return;
        }
        throw new IllegalArgumentException(object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass().getCanonicalName());
    }

    private void validateAs(ModelWithMap object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ModelWithMap.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getMap()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getMap(), true, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    public static void validateInternalFor_fixtures_maps_ModelWithMap(Map<Object, Object> map) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ModelWithMap.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("map");
        raveErrors = mergeErrors(raveErrors, checkNullable(map, true, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
