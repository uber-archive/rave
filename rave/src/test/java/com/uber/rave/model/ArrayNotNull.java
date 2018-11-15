package com.uber.rave.model;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.uber.rave.AnnotationSpecs;
import com.uber.rave.ArrayCreator;
import com.uber.rave.CollectionCreator;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.ParameterizedBuilder;
import com.uber.rave.StringCreator;
import com.uber.rave.annotation.Validated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple model that holds arrays and collections that are also models.
 */
@Validated(factory = TestFactory.class)
public class ArrayNotNull {

    private final Collection<SingleMethodSampleModel> mSingles;

    private final String[] mStrings;

    public ArrayNotNull(String[] strings, Collection<SingleMethodSampleModel> singles) {
        mStrings = strings;
        mSingles = singles;
    }

    @NonNull
    @Size(min = 1, max = 3)
    public Collection<SingleMethodSampleModel> getSingles() {
        return mSingles;
    }

    @Size(min = 5, max = 20)
    @NonNull
    public String[] getStringsArray() {
        return mStrings;
    }

    @NonNull
    public String methodShouldntGenerateValidation(Object someInput) {
        return "some return value.";
    }

    public String toString() {
        String msg = "";
        if (mStrings != null) {
            msg += "Array is of size:" + mStrings.length + "\n";
        }
        if (mSingles != null) {
            msg += "Collection is of size:" + mSingles.size() + "\n";
            for (SingleMethodSampleModel model : mSingles) {
                if (model != null) {
                    msg += "\tModel:" + model.toString() + "\n";
                }
            }
        } else {
            msg += "Collection is null.\n";
        }
        return msg;
    }

    public static class Builder implements ParameterizedBuilder<ArrayNotNull> {

        List<ArrayNotNull> validModels = new ArrayList<>();
        List<ArrayNotNull> invalidModels = new ArrayList<>();
        ObjectCreatorIncrementer mIncrementer;
        private final ArrayCreator<String> mStringArrayCreator;
        private final CollectionCreator<SingleMethodSampleModel> mSinglesCreator;

        public Builder() {
            StringCreator forCollectionStringCreator = StringCreator.getBasicStringCreator();
            AnnotationSpecs spec = new AnnotationSpecs.Builder().setSize(5, 20, 1).setIsNullable(false).build();
            mStringArrayCreator = new ArrayCreator<>(spec, forCollectionStringCreator, String.class);
            AnnotationSpecs spec2 = new AnnotationSpecs.Builder().setSize(1, 3, 1).setIsNullable(false).build();
            mSinglesCreator = new CollectionCreator<>(spec2, new SingleMethodSampleModel.Builder());
            mIncrementer = new ObjectCreatorIncrementer(mStringArrayCreator, mSinglesCreator);
        }

        @Override
        public Collection<ArrayNotNull> getValidCases() {
            while (mIncrementer.hasValidPermutations()) {
                validModels.add(new ArrayNotNull(mStringArrayCreator.getValidItem(), mSinglesCreator.getValidItem()));
                mIncrementer.incrementValidCreators();
            }
            return validModels;
        }

        @Override
        public Collection<ArrayNotNull> getInvalidCases() {
            while (mIncrementer.hasInvalidPermutations()) {
                invalidModels.add(new ArrayNotNull(mStringArrayCreator.getInvalidItem(),
                        mSinglesCreator.getInvalidItem()));
                mIncrementer.incrementInvalidCreators();
            }
            return invalidModels;
        }
    }
}
