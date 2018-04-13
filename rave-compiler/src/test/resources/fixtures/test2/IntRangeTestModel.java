package com.uber.rave.model;

import android.support.annotation.IntRange;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.IntCreator;
import com.uber.rave.ObjectCreator;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.annotation.Validated;
import com.uber.rave.compiler.MyFactory;

/**
 * Test IntRange annotation code generation.
 */
@Validated(factory = MyFactory.class)
public class IntRangeTestModel {
    private static final int FROM = -15;
    private static final int TO = 1000;

    private final int value;

    public IntRangeTestModel(int value) {
        this.value = value;
    }

    @IntRange(from = FROM, to = TO)
    public int getValue() {
        return value;
    }

    public static class Builder extends ObjectCreator<com.uber.rave.model.IntRangeTestModel> {

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
                addValidType(new com.uber.rave.model.IntRangeTestModel(matchIntRangeCreator.getValidItem()));
                incrementer.incrementValidCreators();
            }
        }

        public void buildInvalidCases() {
            while (incrementer.hasInvalidPermutations()) {
                addInvalidType(new com.uber.rave.model.IntRangeTestModel(matchIntRangeCreator.getInvalidItem()));
                incrementer.incrementInvalidCreators();
            }
        }
    }
}
