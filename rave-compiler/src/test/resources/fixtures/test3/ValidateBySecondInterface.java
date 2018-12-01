package fixtures.test3;

import androidx.annotation.Nullable;

import fixtures.SampleFactory;
import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public interface ValidateBySecondInterface {
    @Nullable
    String getANullableString();
}
