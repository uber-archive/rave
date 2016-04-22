package com.ubercab.rave.model;

import android.support.annotation.IntRange;

import com.ubercab.rave.AnnotationSpecs;
import com.ubercab.rave.LongCreator;
import com.ubercab.rave.ObjectCreator;
import com.ubercab.rave.ObjectCreatorIncrementer;
import com.ubercab.rave.annotation.Validated;

/**
 * Test IntRange annotation code generation.
 */
@Validated(factory = TestFactory.class)
public class IntRangeTestModel {
    private static final long FROM = -15L;
    private static final long TO = 1000L;

    private final long value;

    public IntRangeTestModel(long value) {
        this.value = value;
    }

    @IntRange(from = -15, to = 1000)
    public long getValue() {
        return value;
    }

    public static class Builder extends ObjectCreator<IntRangeTestModel> {

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
