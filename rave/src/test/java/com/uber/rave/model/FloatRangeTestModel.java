package com.uber.rave.model;

import androidx.annotation.FloatRange;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.DoubleCreator;
import com.uber.rave.ObjectCreator;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.annotation.Validated;

/**
 * A class that uses {@link FloatRange} annotation.
 */
@Validated(factory = TestFactory.class)
public class FloatRangeTestModel {
    private static final double FROM = -15.5d;
    private static final double TO = 1000.9d;

    private final double value;

    public FloatRangeTestModel(double value) {
        this.value = value;
    }

    @FloatRange(from = FROM, to = TO)
    public double getValue() {
        return value;
    }

    public static class Builder extends ObjectCreator<FloatRangeTestModel> {

        private final DoubleCreator matchFloatRangeCreator;
        private final ObjectCreatorIncrementer incrementer;

        public Builder() {
            AnnotationSpecs spec = new AnnotationSpecs.Builder()
                    .setIsNullable(false)
                    .setFloatRange(FROM, TO)
                    .build();
            matchFloatRangeCreator = new DoubleCreator(spec);
            incrementer = new ObjectCreatorIncrementer(matchFloatRangeCreator);
            buildValidCases();
            buildInvalidCases();
        }

        public void buildValidCases() {
            while (incrementer.hasValidPermutations()) {
                addValidType(new FloatRangeTestModel(matchFloatRangeCreator.getValidItem()));
                incrementer.incrementValidCreators();
            }
        }

        public void buildInvalidCases() {
            while (incrementer.hasInvalidPermutations()) {
                addInvalidType(new FloatRangeTestModel(matchFloatRangeCreator.getInvalidItem()));
                incrementer.incrementInvalidCreators();
            }
        }
    }
}
