package fixtures;

import android.support.annotation.NonNull;

import com.ubercab.rave.BaseValidator;
import com.ubercab.rave.ValidatorFactory;


public final class SampleFactory implements ValidatorFactory {
    @NonNull
    @Override
    public BaseValidator generateValidator() {
        return new SampleFactory_Generated_Validator();
    }
}
