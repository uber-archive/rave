package com.uber.rave.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.BooleanCreator;
import com.uber.rave.CollectionCreator;
import com.uber.rave.annotation.MustBeFalse;
import com.uber.rave.annotation.MustBeTrue;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.ParameterizedBuilder;
import com.uber.rave.StringCreator;
import com.uber.rave.annotation.Validated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sample model to validate.
 */
@Validated(factory = TestFactory.class)
public class MultiMethodSampleModel {

    final NonAnnotated annotatedObject;
    String notNullField;
    String canBeNullField;
    String betweenOneAndFive;
    private final Collection<String> names;
    private final boolean isFalse;
    private boolean mustBeTrue;

    public MultiMethodSampleModel(
            NonAnnotated annotatedObject,
            String notNullField,
            String canBeNullField,
            String betweenOneAndFive,
            boolean mustBeTrue,
            Collection<String> names) {
        this.annotatedObject = annotatedObject;
        this.notNullField = notNullField;
        this.canBeNullField = canBeNullField;
        this.betweenOneAndFive = betweenOneAndFive;
        this.names = names;
        isFalse = false;
        this.mustBeTrue = mustBeTrue;
        getIsFalse();
    }

    @Nullable
    public NonAnnotated getNonAnnotatedObject() {
        return annotatedObject;
    }

    /**
     * @return Simple example of using Nonnull annotation.
     */
    @NonNull
    public String getNotNullField() {
        return notNullField;
    }

    /**
     * @return Simple example of using Nullable annotation.
     *
     */
    @Nullable
    public String getCanBeNullField() {
        return canBeNullField;
    }

    /**
     * @return Simple example of using size annotation on a string.
     */
    @Size(min = 1, max = 5)
    public String getBetweenOneAndFive() {
        return betweenOneAndFive;
    }

    /**
     * @return Simple example of using size annotation.
     */
    @Size(min = 1, max = 5)
    public Collection<String> getNames() {
        return names;
    }

    /**
     * @return Simple example of using {@link MustBeFalse} annotation.
     */
    @MustBeFalse
    public boolean getIsFalse() {
        return isFalse;
    }

    /**
     * @return Simple example of using {@link MustBeTrue} annotation.
     */
    @MustBeTrue
    public boolean getIsTrue() {
        return mustBeTrue;
    }

    public String toString() {
        String result = "";
        result += "nonAnnotatedObject:" + getNonAnnotatedObject() + "\n";
        result += "notNullField:" + getNotNullField() + "\n";
        result += "getCanBeNullField:" + getCanBeNullField() + "\n";
        result += "getBetweenOneAndFive:" + getBetweenOneAndFive() + "\n";
        if (getNames() != null) {
            result += "getNames size:" + getNames().size() + "\n";
        }
        result += "isFalse:" + isFalse + "\n";
        result += "isTrue:" + mustBeTrue + "\n";
        return result;
    }

    /**
     * The parameterized builder for {@link MultiMethodSampleModel}.
     */
    public static class Builder implements ParameterizedBuilder<MultiMethodSampleModel> {
        List<MultiMethodSampleModel> validModels = new ArrayList<>();
        List<MultiMethodSampleModel> invalidModels = new ArrayList<>();
        ObjectCreatorIncrementer mIncrementer;
        private final StringCreator nonAnnotatedString = new StringCreator(false);
        private final StringCreator notNullFieldCreator = new StringCreator(false);
        private final StringCreator canBeNullFieldCreator = new StringCreator(true);
        private final StringCreator betweenOneAndFive = new StringCreator(1, 5, 1, true);
        private final BooleanCreator mustBeTrue = new BooleanCreator(true);
        private final CollectionCreator<String> names;

        public Builder() {
            StringCreator forCollectionStringCreator = StringCreator.getBasicStringCreator();
            AnnotationSpecs spec = new AnnotationSpecs.Builder().setSize(1, 5, 1).setIsNullable(true).build();
            names = new CollectionCreator<String>(spec, forCollectionStringCreator);
            mIncrementer = new ObjectCreatorIncrementer(nonAnnotatedString, notNullFieldCreator,
                    canBeNullFieldCreator, betweenOneAndFive, mustBeTrue, names);
        }
        @Override
        public Collection<MultiMethodSampleModel> getValidCases() {
            while (mIncrementer.hasValidPermutations()) {
                validModels.add(new MultiMethodSampleModel(
                        new NonAnnotated(nonAnnotatedString.getValidItem()),
                        notNullFieldCreator.getValidItem(),
                        canBeNullFieldCreator.getValidItem(), betweenOneAndFive.getValidItem(),
                        mustBeTrue.getValidItem(), names.getValidItem()));
                mIncrementer.incrementValidCreators();
            }
            return validModels;
        }

        @Override
        public Collection<MultiMethodSampleModel> getInvalidCases() {
            while (mIncrementer.hasInvalidPermutations()) {
                invalidModels.add(new MultiMethodSampleModel(
                        new NonAnnotated(nonAnnotatedString.getInvalidItem()),
                        notNullFieldCreator.getInvalidItem(),
                        canBeNullFieldCreator.getInvalidItem(), betweenOneAndFive.getInvalidItem(),
                        mustBeTrue.getInvalidItem(), names.getInvalidItem()));
                mIncrementer.incrementInvalidCreators();
            }
            return invalidModels;
        }
    }
}
