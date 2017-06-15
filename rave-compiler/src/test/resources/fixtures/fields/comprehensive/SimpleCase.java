package fixtures.fields.comprehensive;

import fixtures.fields.FieldsOnlyFactory;

import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.StringDef;

import com.uber.rave.annotation.Excluded;
import com.uber.rave.annotation.MustBeFalse;
import com.uber.rave.annotation.MustBeTrue;
import com.uber.rave.annotation.Validated;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

@Validated(factory = FieldsOnlyFactory.class)
public class SimpleCase {
    private static final String shouldCauseNoGeneration = "";

    @UberPoolState
    private String notNullField;
    private String canBeNullField;
    private String betweenOneAndFive;
    private final List<String> names;
    private final boolean isFalse;
    @IntRestrict
    private Integer testIntdef1;
    @IntRestrict
    private int testIntdef2;
    private float wontGenerateAnything;
    @FloatRange(from = .1, to = .5)
    private float testFloatDef;
    private Map<String, SimpleCase> map;
    @FloatRange(from = .1, to = .5)
    @Excluded private float shouldExclude;

    private static final String MATCHED = "Matched";
    private static final String MATCHING = "Matching";
    private static final String NOT_MATCHED = "NotMatched";

    @StringDef({MATCHED, MATCHING, NOT_MATCHED})
    @Retention(RetentionPolicy.SOURCE)
    @interface UberPoolState { }

    @IntDef({1, 2, 3})
    @Retention(RetentionPolicy.SOURCE)
    @interface IntRestrict { }

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
    public static String staticMethodShouldntGenerateAnything() {
        return "";
    };

    @UberPoolState
    public String getUberPoolState() {
        return "Hey now";
    }

    @Validated(factory = FieldsOnlyFactory.class)
    public static class SomeInnerClass {

        private String someString;
        public SomeInnerClass() {}

        @NonNull
        public String getString() { return "someString"; }
    }
}
