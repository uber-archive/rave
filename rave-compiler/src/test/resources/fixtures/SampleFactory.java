package fixtures;

import androidx.annotation.NonNull;

import com.uber.rave.BaseValidator;
import com.uber.rave.ValidatorFactory;


public final class SampleFactory implements ValidatorFactory {
    @NonNull
    @Override
    public BaseValidator generateValidator() {
        return new SampleFactory_Generated_Validator();
    }
}
