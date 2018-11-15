package com.uber.rave.model;

import androidx.annotation.IntRange;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.IntCreator;
import com.uber.rave.ObjectCreator;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.annotation.Validated;

/**
 * Test IntRange annotation code generation.
 */
@Validated(factory = TestFactory.class)
public class IntRangeTestModel {
    private static final int FROM = -15;
    private static final int TO = 1000;

    private final int value;

    public IntRangeTestModel(int value) {
        this.value = value;
    }

    @IntRange(from = -15, to = 1000)
    public int getValue() {
        return value;
    }

    public static class Builder extends ObjectCreator<IntRangeTestModel> {

        private final IntCreator matchIntRangeCreator;
        private final ObjectCreatorIncrementer incrementer;

        public Builder() {
            AnnotationSpecs spec = new AnnotationSpecs.Builder()
                    .setIsNullable(false)
                    .setIntRange(FROM, TO)
                    .build();
            matchIntRangeCreator = new IntCreator(spec);
            incrementer = new ObjectCreatorIncrementer(matchIntRangeCreator);
            buildValidCases();
            buildInvalidCases();
        }

        public void buildValidCases() {
            while (incrementer.hasValidPermutations()) {
                addValidType(new IntRangeTestModel(matchIntRangeCreator.getValidItem()));
                incrementer.incrementValidCreators();
            }
        }

        public void buildInvalidCases() {
            while (incrementer.hasInvalidPermutations()) {
                addInvalidType(new IntRangeTestModel(matchIntRangeCreator.getInvalidItem()));
                incrementer.incrementInvalidCreators();
            }
        }
    }
}
