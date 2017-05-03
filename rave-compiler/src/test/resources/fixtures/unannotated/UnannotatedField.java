package fixtures.unannotated;

import fixtures.SampleFactory;

import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class UnannotatedField {
    String nullableField;

    public UnannotatedField (String nullableField) {
        this.nullableField = nullableField;
    }

    public String getNullableField() {
        return nullableField;
    }

    public int getPrimativeField() {
        return 0;
    }
}
