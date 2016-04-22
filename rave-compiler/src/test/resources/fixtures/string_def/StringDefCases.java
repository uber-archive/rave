package fixtures.string_def;

import fixtures.SampleFactory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import com.ubercab.rave.annotation.Validated;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

@Validated(factory = SampleFactory.class)
public class StringDefCases {

    private static final String MATCHED = "Matched";
    private static final String MATCHING = "Matching";
    private static final String NOT_MATCHED = "NotMatched";

    @StringDef({MATCHED, MATCHING, NOT_MATCHED})
    @Retention(RetentionPolicy.SOURCE)
    @interface SampleStringDef { }

    @SampleStringDef
    @NonNull
    public String getNonNullableString1() {
        return "Hey now";
    }

    @Nullable
    @SampleStringDef
    public String getNullableString() {
        return "Hey now";
    }

    @SampleStringDef
    public String getNonNullabeString2() {
        return "Hey now";
    }

    @SampleStringDef
    public String[] getNonNullableArray() {
        return new String[0];
    }

    @SampleStringDef
    @NonNull
    public String[] getNonNullableArray2() {
        return new String[0];
    }

    @SampleStringDef
    @Nullable
    public String[] getNullableArray() {
        return new String[0];
    }

    @SampleStringDef
    public List<String> getNonNullList() {
        return Collections.EMPTY_LIST;
    }

    @SampleStringDef
    @Nullable
    public List<String> getNullableList() {
        return Collections.EMPTY_LIST;
    }

    @SampleStringDef
    @NonNull
    public List<String> getNonNullableList2() {
        return Collections.EMPTY_LIST;
    }
}
