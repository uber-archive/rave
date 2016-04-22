package com.uber.rave.model;

import android.support.annotation.IntRange;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.LongCreator;
import com.uber.rave.ObjectCreator;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.annotation.Validated;
import com.uber.rave.compiler.MyFactory;

/**
 * Test IntRange annotation code generation.
 */
@Validated(factory = MyFactory.class)
public class IntRangeTestModel {
    private static final long FROM = -15L;
    private static final long TO = 1000L;

    private final long value;

    public IntRangeTestModel(long value) {
        this.value = value;
    }

    @IntRange(from = FROM, to = TO)
    public long getValue() {
        return value;
    }

    public static class Builder extends ObjectCreator<com.uber.rave.model.IntRangeTestModel> {

        private final LongCreator matchIntRangeCreator;
        private final ObjectCreatorIncrementer incrementer;

        public Builder() {
            AnnotationSpecs spec = new AnnotationSpecs.Builder()
                    .setIsNullable(false)
                    .setIntRange(FROM, TO)
                    .build();
            matchIntRangeCreator = new LongCreator(spec);
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
