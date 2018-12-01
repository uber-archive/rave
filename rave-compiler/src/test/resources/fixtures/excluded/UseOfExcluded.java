package fixtures.test1;

import fixtures.SampleFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.uber.rave.annotation.Excluded;
import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class UseOfExcluded {
    String notNullField;
    String canBeNullField;

    public UseOfExcluded (
            String notNullField,
            String canBeNullField) {
        this.notNullField = notNullField;
        this.canBeNullField = canBeNullField;
    }

    @Excluded
    @NonNull
    public String getNotNullField() {
        return notNullField;
    }

    @Nullable
    public String getCanBeNullField() {
        return canBeNullField;
    }
}
