package fixtures.test3;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import fixtures.SampleFactory;
import com.uber.rave.annotation.Validated;

/**
 * Example of an interface using RAVE.
 */
@Validated(factory = SampleFactory.class)
public interface ValidateByInterface extends ValidateBySecondInterface {

    /**
     * @return Example of a method using RAVE.
     */
    @NonNull
    @Size(min = 1, max = 2)
    String getNonNullString();
}
