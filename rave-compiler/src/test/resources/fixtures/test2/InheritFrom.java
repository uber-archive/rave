package com.uber.rave.model;

import androidx.annotation.NonNull;

import com.uber.rave.model.SingleMethodSampleModel;
import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.ParameterizedBuilder;
import com.uber.rave.StringCreator;
import com.uber.rave.annotation.Validated;
import com.uber.rave.compiler.MyFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sample class that shows how inheritence would work with validation.
 */
@Validated(factory = MyFactory.class)
public class InheritFrom extends com.uber.rave.model.SingleMethodSampleModel implements
        com.uber.rave.model.ValidateByInterface {

    private final String nonNullString;

    public InheritFrom(String nonNullString, String superClassNonNullField) {
        super(superClassNonNullField, SingleMethodSampleModel.MATCHED1);
        this.nonNullString = nonNullString;
    }

    /**
     * @return Returns some nonnull string. See {@link com.uber.rave.model.ValidateByInterface} for additional restrictions on this
     * method.
     */
    @NonNull
    @Override
    public String getNonNullString() {
        return nonNullString;
    }

    public String toString() {
        String result = "";
        result += "getNonNullString:" + getNonNullString() + "\n";
        result += "getNotNullField" + getNotNullField() + "\n";
        return result;
    }

    public static class Builder implements ParameterizedBuilder<com.uber.rave.model.InheritFrom> {

        List<com.uber.rave.model.InheritFrom> validModels = new ArrayList<>();
        List<com.uber.rave.model.InheritFrom> invalidModels = new ArrayList<>();
        ObjectCreatorIncrementer mIncrementer;
        // This size constraints come from the inherited interface.
        private final StringCreator nonNullString = new StringCreator(0, 4, 1, false);
        private final StringCreator nonNullField1to20Multiof2 = new StringCreator(1, 20, 2, false);

        public Builder() {
            mIncrementer = new ObjectCreatorIncrementer(nonNullString, nonNullField1to20Multiof2);
        }

        @Override
        public Collection<com.uber.rave.model.InheritFrom> getValidCases() {
            while (mIncrementer.hasValidPermutations()) {
                validModels.add(
                        new com.uber.rave.model.InheritFrom(nonNullString.getValidItem(), nonNullField1to20Multiof2.getValidItem()));
                mIncrementer.incrementValidCreators();
            }
            return validModels;
        }

        @Override
        public Collection<com.uber.rave.model.InheritFrom> getInvalidCases() {
            while (mIncrementer.hasInvalidPermutations()) {
                invalidModels.add(
                        new com.uber.rave.model.InheritFrom(nonNullString.getInvalidItem(), nonNullField1to20Multiof2.getInvalidItem()));
                mIncrementer.incrementInvalidCreators();
            }
            return invalidModels;
        }
    }
}
