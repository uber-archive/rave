package fixtures.fields;

import android.support.annotation.NonNull;

import com.uber.rave.BaseValidator;
import com.uber.rave.Validator;
import com.uber.rave.ValidatorFactory;


public final class NotStrictFieldsOnlyFactory implements ValidatorFactory {
    @Override
    public BaseValidator generateValidator() {
        return new NotStrictFieldsOnlyFactory_Generated_Validator();
    }
}
