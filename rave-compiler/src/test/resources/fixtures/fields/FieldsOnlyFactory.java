package fixtures.fields;

import androidx.annotation.NonNull;

import com.uber.rave.BaseValidator;
import com.uber.rave.Validator;
import com.uber.rave.ValidatorFactory;


@Validator(mode = Validator.Mode.STRICT)
public final class FieldsOnlyFactory implements ValidatorFactory {
    @Override
    public BaseValidator generateValidator() {
        return new FieldsOnlyFactory_Generated_Validator();
    }
}
