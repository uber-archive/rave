package com.ubercab.rave.model;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.ubercab.rave.AnnotationSpecs;
import com.ubercab.rave.ArrayCreator;
import com.ubercab.rave.CollectionCreator;
import com.ubercab.rave.ObjectCreatorIncrementer;
import com.ubercab.rave.ParameterizedBuilder;
import com.ubercab.rave.StringCreator;
import com.ubercab.rave.annotation.Validated;
import com.ubercab.rave.compiler.MyFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple model that holds arrays and collections that are also models.
 */
@Validated(factory = MyFactory.class)
public class ArrayNotNull {

    private final String[] strings;
    private final Collection<SingleMethodSampleModel> singles;

    public ArrayNotNull(String[] strings, Collection<SingleMethodSampleModel> singles) {
        this.strings = strings;
        this.singles = singles;
    }

    @NonNull
    @Size(min = 1, max = 3)
    public Collection<SingleMethodSampleModel> getSingles() {
        return singles;
    }

    @Size(min = 5, max = 20)
    @NonNull
    public String[] getStringsArray() {
        return strings;
    }

    @NonNull
    public String methodShouldntGenerateValidatoin(Object someInput) {
        return "some return value.";
    }

    public String toString() {
        String msg = "";
        if (strings != null) {
            msg += "Array is of size:" + strings.length + "\n";
        }
        if (singles != null) {
            msg += "Collection is of size:" + singles.size() + "\n";
            for (SingleMethodSampleModel model : singles) {
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

        private final ArrayCreator<String> stringArrayCreator;
        private final CollectionCreator<SingleMethodSampleModel> mSinglesCreator;
        private final List<ArrayNotNull> validModels = new ArrayList<>();
        private final List<ArrayNotNull> invalidModels = new ArrayList<>();
        private final ObjectCreatorIncrementer mIncrementer;

        public Builder() {
            StringCreator forCollectionStringCreator = StringCreator.getBasicStringCreator();
            AnnotationSpecs spec = new AnnotationSpecs.Builder().setSize(5, 20, 1).setIsNullable(false).build();
            stringArrayCreator = new ArrayCreator<>(spec, forCollectionStringCreator, String.class);
            AnnotationSpecs spec2 = new AnnotationSpecs.Builder().setSize(1, 3, 1).setIsNullable(false).build();
            mSinglesCreator = new CollectionCreator<>(spec2, new SingleMethodSampleModel.Builder());
            mIncrementer = new ObjectCreatorIncrementer(stringArrayCreator, mSinglesCreator);
        }

        @Override
        public Collection<ArrayNotNull> getValidCases() {
            while (mIncrementer.hasValidPermutations()) {
                validModels.add(new ArrayNotNull(stringArrayCreator.getValidItem(), mSinglesCreator.getValidItem()));
                mIncrementer.incrementValidCreators();
            }
            return validModels;
        }

        @Override
        public Collection<ArrayNotNull> getInvalidCases() {
            while (mIncrementer.hasInvalidPermutations()) {
                invalidModels.add(new ArrayNotNull(stringArrayCreator.getInvalidItem(),
                        mSinglesCreator.getInvalidItem()));
                mIncrementer.incrementInvalidCreators();
            }
            return invalidModels;
        }
    }
}
