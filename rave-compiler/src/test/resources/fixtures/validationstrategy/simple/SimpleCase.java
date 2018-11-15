package fixtures.validationstrategy.simple;

import fixtures.validationstrategy.StrictModeFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uber.rave.annotation.Validated;

@Validated(factory = StrictModeFactory.class)
public class SimpleCase {
    String notNullField;
    String canBeNullField;
    String unannotatedField;

    public SimpleCase (
            String notNullField,
            String canBeNullField,
            String unannotatedField) {
        this.notNullField = notNullField;
        this.canBeNullField = canBeNullField;
        this.unannotatedField = unannotatedField;
    }

    @NonNull
    public String getNotNullField() {
        return notNullField;
    }

    @Nullable
    public String getCanBeNullField() {
        return canBeNullField;
    }

    public String getUnannotatedField() {
        return unannotatedField;
    }
}
