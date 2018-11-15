package fixtures.test4;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import fixtures.SampleFactory;
import com.uber.rave.annotation.Validated;

/**
 * Example of an interface using RAVE annotations.
 */
@Validated(factory = SampleFactory.class)
public interface ValidateByInterface {

    /**
     * @return Example of a method using RAVE.
     */
    @NonNull
    @Size(min = 1, max = 2)
    String getNonNullString();
}
