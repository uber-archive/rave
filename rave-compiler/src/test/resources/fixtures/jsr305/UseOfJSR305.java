package fixtures.jsr305;

import fixtures.SampleFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class UseOfJSR305 {
    String notNullField;
    String canBeNullField;

    public UseOfJSR305 (
            String notNullField,
            String canBeNullField) {
        this.notNullField = notNullField;
        this.canBeNullField = canBeNullField;
    }

    @Nonnull
    public String getNotNullField() {
        return notNullField;
    }

    @Nullable
    public String getCanBeNullField() {
        return canBeNullField;
    }
}
