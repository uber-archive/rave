package com.ubercab.rave.model;

import android.support.annotation.IntDef;

import com.ubercab.rave.AnnotationSpecs;
import com.ubercab.rave.LongCreator;
import com.ubercab.rave.ObjectCreator;
import com.ubercab.rave.ObjectCreatorIncrementer;
import com.ubercab.rave.annotation.Validated;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Validated(factory = TestFactory.class)
public class IntDefModel {
    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_TABS = 2;

    private final long value;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS})
    public @interface NavigationMode { }

    public IntDefModel(long value) {
        this.value = value;
    }

    @NavigationMode
    public long getStandard() {
        return value;
    }

    public static class Builder extends ObjectCreator<IntDefModel> {

        private final LongCreator matchIntDefStringCreator;
        private final ObjectCreatorIncrementer incrementer;

        public Builder() {
            AnnotationSpecs spec = new AnnotationSpecs.Builder()
                    .setIsNullable(false)
                    .setIntDef(NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS)
                    .build();
            matchIntDefStringCreator = new LongCreator(spec);
            incrementer = new ObjectCreatorIncrementer(matchIntDefStringCreator);
            buildValidCases();
            buildInvalidCases();
        }

        public void buildValidCases() {
            while (incrementer.hasValidPermutations()) {
                addValidType(new IntDefModel(matchIntDefStringCreator.getValidItem()));
                incrementer.incrementValidCreators();
            }
        }

        public void buildInvalidCases() {
            while (incrementer.hasInvalidPermutations()) {
                addInvalidType(new IntDefModel(matchIntDefStringCreator.getInvalidItem()));
                incrementer.incrementInvalidCreators();
            }
        }
    }
}
