package fixtures.test3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fixtures.SampleFactory;

import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class InheritFrom extends ValidateSample2 implements ValidateByInterface {

    protected InheritFrom(String notNullField) {
        super(notNullField);
    }

    @NonNull
    @Override
    public String getNonNullString() {
        return "Fooo";
    }

    @Nullable
    public String getANullableString() { return null; }
}
