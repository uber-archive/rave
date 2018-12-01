package com.uber.rave.model;

import androidx.annotation.IntDef;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.IntCreator;
import com.uber.rave.ObjectCreator;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.annotation.Validated;
import com.uber.rave.compiler.MyFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Validated(factory = MyFactory.class)
public class IntDefModel {

    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_TABS = 2;

    private final int value;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS})
    public @interface NavigationMode { }

    public IntDefModel(int value) {
        this.value = value;
    }

    @NavigationMode
    public int getStandard() {
        return value;
    }

    public static class Builder extends ObjectCreator<IntDefModel> {

        private final IntCreator matchIntDefCreator;
        private final ObjectCreatorIncrementer incrementer;

        public Builder() {
            AnnotationSpecs spec = new AnnotationSpecs.Builder()
                    .setIsNullable(false)
                    .setIntDef(NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS)
                    .build();
            matchIntDefCreator = new IntCreator(spec);
            incrementer = new ObjectCreatorIncrementer(matchIntDefCreator);
            buildValidCases();
            buildInvalidCases();
        }

        public void buildValidCases() {
            while (incrementer.hasValidPermutations()) {
                addValidType(new IntDefModel(matchIntDefCreator.getValidItem()));
                incrementer.incrementValidCreators();
            }
        }

        public void buildInvalidCases() {
            while (incrementer.hasInvalidPermutations()) {
                addInvalidType(new IntDefModel(matchIntDefCreator.getInvalidItem()));
                incrementer.incrementInvalidCreators();
            }
        }
    }
}
