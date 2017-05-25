package fixtures.jsr305;

import fixtures.SampleFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class JSR305Conflict {
    String canBeNullField;

    public JSR305Conflict (String canBeNullField) {
        this.canBeNullField = canBeNullField;
    }

    @Nonnull
    @Nullable
    public String getCanBeNullField() {
        return canBeNullField;
    }
}
