package fixtures.test3;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.ubercab.rave.annotation.MustBeFalse;
import com.ubercab.rave.annotation.Validated;

import fixtures.SampleFactory;

import java.util.List;

/**
 * Sample model to validate.
 */
@Validated(factory = SampleFactory.class)
public class ValidateSample {

    String notNullField;
    String canBeNullField;
    String betweenOneAndFive;
    private final List<String> names;
    private final boolean isFalse;

    protected ValidateSample(
            String notNullField,
            String canBeNullField,
            String betweenOneAndFive,
            List<String> names) {
        this.notNullField = notNullField;
        this.canBeNullField = canBeNullField;
        this.betweenOneAndFive = betweenOneAndFive;
        this.names = names;
        isFalse = false;
        getIsFalse();
    }

    /**
     * @return Simple example of using Nonnull annotation.
     */
    @NonNull
    public String getNotNullField() {
        return notNullField;
    }

    /**
     * @return Simple example of using Nullable annotation.
     *
     */
    @Nullable
    public String getCanBeNullField() {
        return canBeNullField;
    }

    /**
     * @return Simple example of using size annotation on a string.
     */
    @Size(min = 1, max = 5)
    public String getBetweenOneAndFive() {
        return betweenOneAndFive;
    }

    /**
     * @return Simple example of using size annotation.
     */
    @Size(min = 1, max = 5)
    public List<String> getNames() {
        return names;
    }

    /**
     * @return Simple example of using {@link MustBeFalse} annotation.
     */
    @MustBeFalse
    public boolean getIsFalse() {
        return isFalse;
    }
}
