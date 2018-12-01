package com.uber.rave.model;

import android.support.annotation.LongDef;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.LongCreator;
import com.uber.rave.ObjectCreator;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.annotation.Validated;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Validated(factory = TestFactory.class)
public class LongDefModel {
    public static final long NAVIGATION_MODE_STANDARD = 0L;
    public static final long NAVIGATION_MODE_LIST = 1L;
    public static final long NAVIGATION_MODE_TABS = 2L;

    private final long value;

    @Retention(RetentionPolicy.SOURCE)
    @LongDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS})
    public @interface NavigationMode { }

    public LongDefModel(long value) {
        this.value = value;
    }

    @NavigationMode
    public long getStandard() {
        return value;
    }

    public static class Builder extends ObjectCreator<LongDefModel> {

        private final LongCreator matchLongDefStringCreator;
        private final ObjectCreatorIncrementer incrementer;

        public Builder() {
            AnnotationSpecs spec = new AnnotationSpecs.Builder()
                    .setIsNullable(false)
                    .setLongDef(NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS)
                    .build();
            matchLongDefStringCreator = new LongCreator(spec);
            incrementer = new ObjectCreatorIncrementer(matchLongDefStringCreator);
            buildValidCases();
            buildInvalidCases();
        }

        public void buildValidCases() {
            while (incrementer.hasValidPermutations()) {
                addValidType(new LongDefModel(matchLongDefStringCreator.getValidItem()));
                incrementer.incrementValidCreators();
            }
        }

        public void buildInvalidCases() {
            while (incrementer.hasInvalidPermutations()) {
                addInvalidType(new LongDefModel(matchLongDefStringCreator.getInvalidItem()));
                incrementer.incrementInvalidCreators();
            }
        }
    }
}
