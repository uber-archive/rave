package fixtures.test1;

import fixtures.SampleFactory;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.StringDef;

import com.uber.rave.annotation.MustBeFalse;
import com.uber.rave.annotation.MustBeTrue;
import com.uber.rave.annotation.Validated;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@Validated(factory = SampleFactory.class)
public class SimpleCase implements NothingInterface {
    String notNullField;
    String canBeNullField;
    String betweenOneAndFive;
    private final List<String> names;
    private final boolean isFalse;

    private static final String MATCHED = "Matched";
    private static final String MATCHING = "Matching";
    private static final String NOT_MATCHED = "NotMatched";

    @StringDef({MATCHED, MATCHING, NOT_MATCHED})
    @Retention(RetentionPolicy.SOURCE)
    @interface UberPoolState { }

    public SimpleCase (
            String notNullField,
            String canBeNullField,
            String betweenOneAndFive,
            List<String> names) {
        this.notNullField = notNullField;
        this.canBeNullField = canBeNullField;
        this.betweenOneAndFive = betweenOneAndFive;
        this.names = names;
        isFalse = false;
    }

    @NonNull
    public String getNotNullField() {
        return notNullField;
    }

    @Nullable
    public String getCanBeNullField() {
        return canBeNullField;
    }

    @NonNull
    String methodNotVisible() {
        return "";
    }

    @Size(min = 1, max = 5)
    public String getBetweenOneAndFive() {
        return betweenOneAndFive;
    }

    @Size(5)
    public List<String> getNames() {
        return names;
    }

    @Size(min = 1, max = 5)
    public String[] getArrayNames() {
        return names.toArray(new String[names.size()]);
    }

    @MustBeFalse
    public boolean getIsFalse() {
        return isFalse;
    }

    @MustBeTrue
    public boolean getIsTrue() {
        return true;
    }

    @MustBeTrue
    public boolean returnBoolean(boolean bool) {
        return bool;
    }

    @NonNull
    public static String staticMethodShouldntGenerateAnything() {
        return "";
    };

    @UberPoolState
    public String getUberPoolState() {
        return "Hey now";
    }

    @Validated(factory = SampleFactory.class)
    public static class SomeInnerClass {
        public SomeInnerClass() {}

        @NonNull
        public String getString() { return "someString"; }
    }
}
