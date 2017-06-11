package fixtures.fields.no_gen;

import fixtures.fields.NotStrictFieldsOnlyFactory;

import android.support.annotation.NonNull;

import com.uber.rave.annotation.Validated;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Validated(factory = NotStrictFieldsOnlyFactory.class)
public class NoGen {
    private static final String shouldCauseNoGeneration = "";

    // These strings should trigger code gen validation code
    private String notNullField;
    private String canBeNullField;
    private String betweenOneAndFive;
    @NonNull
    private final List<String> names = new LinkedList<>();
    private final boolean isFalse = false;
    private int testIntdef2;
    private float wontGenerateAnything;
    // These map should trigger code gen validation code
    private Map<String, NoGen> map;

    public String getUberPoolState() {
        return "Hey now";
    }
}
