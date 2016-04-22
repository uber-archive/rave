package fixtures.test3;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import fixtures.SampleFactory;
import com.ubercab.rave.annotation.Validated;

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
