package com.uber.rave.model;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.StringDef;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.ObjectCreator;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.StringCreator;
import com.uber.rave.annotation.Validated;
import com.uber.rave.compiler.MyFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A simple example of using RAVE on a model class.
 */
@Validated(factory = MyFactory.class)
public class SingleMethodSampleModel {

    public static final String MATCHED1 = "Matched";
    public static final String MATCHED2 = "Matching";
    public static final String MATCHED3 = "AlsoMatching";

    @StringDef({MATCHED1, MATCHED2, MATCHED3})
    @Retention(RetentionPolicy.SOURCE)
    @interface TestStringDef { }

    private String notNullField;
    private String matchStringDef;

    public SingleMethodSampleModel(String notNullField, String matchStringDef) {
        this.notNullField = notNullField;
        this.matchStringDef = matchStringDef;
    }

    /**
     * @return An example method using the size annotation.
     */
    @Size(min = 1, max = 20, multiple = 2)
    @NonNull
    public String getNotNullField() {
        return notNullField;
    }

    @TestStringDef
    @NonNull
    public String getMatchStringDef() {
        return matchStringDef;
    }

    public static class Builder extends ObjectCreator<SingleMethodSampleModel> {

        ObjectCreatorIncrementer incrementer;
        private final StringCreator notNullStringCreator = new StringCreator(1, 20, 2, false);
        private final StringCreator matchStringDefStringCreator;

        public Builder() {
            AnnotationSpecs spec = new AnnotationSpecs.Builder()
                    .setIsNullable(false)
                    .setStringDef(MATCHED1, MATCHED2, MATCHED3)
                    .build();
            matchStringDefStringCreator = new StringCreator(spec);
            incrementer = new ObjectCreatorIncrementer(notNullStringCreator, matchStringDefStringCreator);
            buildValidCases();
            buildInvalidCases();
        }

        private void buildValidCases() {
            while (incrementer.hasValidPermutations()) {
                addValidType(new SingleMethodSampleModel(notNullStringCreator.getValidItem(),
                        matchStringDefStringCreator.getValidItem()));
                incrementer.incrementValidCreators();
            }
        }

        private void buildInvalidCases() {
            while (incrementer.hasInvalidPermutations()) {
                addInvalidType(new SingleMethodSampleModel(notNullStringCreator.getInvalidItem(),
                        matchStringDefStringCreator.getInvalidItem()));
                incrementer.incrementInvalidCreators();
            }
        }
    }
}
