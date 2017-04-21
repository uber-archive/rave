package fixtures.validationstrategy;

import android.support.annotation.NonNull;

import com.uber.rave.BaseValidator;
import com.uber.rave.Validator;
import com.uber.rave.ValidatorFactory;


@Validator(mode = Validator.Mode.STRICT)
public final class StrictModeFactory implements ValidatorFactory {
    @NonNull
    @Override
    public BaseValidator generateValidator() {
        return new StrictModeFactory_Generated_Validator();
    }
}
