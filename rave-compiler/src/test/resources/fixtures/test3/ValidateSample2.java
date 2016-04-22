package fixtures.test3;

import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.ubercab.rave.annotation.Validated;

import fixtures.SampleFactory;

/**
 * A simple example of using RAVE on a model class.
 */
@Validated(factory = SampleFactory.class)
public class ValidateSample2<T> {
    String notNullField;

    protected ValidateSample2(String notNullField) {
        this.notNullField = notNullField;
    }

    /**
     * @return An example method using the size annotation.
     */
    @Size(min = 1, max = 5)
    @Nullable
    public String getNotNullField() {
        return notNullField;
    }
}
