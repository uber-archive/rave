package com.uber.rave.model;

import androidx.annotation.NonNull;

import com.uber.rave.ObjectCreatorIncrementer;
import com.uber.rave.ParameterizedBuilder;
import com.uber.rave.StringCreator;
import com.uber.rave.annotation.Validated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sample class that shows how inheritence would work with validation.
 */
@Validated(factory = TestFactory.class)
public class InheritFrom extends SingleMethodSampleModel implements ValidateByInterface {

    private final String nonNullString;

    public InheritFrom(String nonNullString, String superClassNonNullField) {
        super(superClassNonNullField, SingleMethodSampleModel.MATCHED1);
        this.nonNullString = nonNullString;
    }

    /**
     * @return Returns some nonnull string. See {@link ValidateByInterface} for additional restrictions on this
     * method.
     */
    @NonNull
    @Override
    public String getNonNullString() {
        return nonNullString;
    }

    @Override
    public String toString() {
        String result = "";
        result += "getNonNullString:" + getNonNullString() + "\n";
        result += "getNotNullField" + getNotNullField() + "\n";
        return result;
    }

    public static class Builder implements ParameterizedBuilder<InheritFrom> {

        List<InheritFrom> validModels = new ArrayList<>();
        List<InheritFrom> invalidModels = new ArrayList<>();
        ObjectCreatorIncrementer mIncrementer;
        // This size constraints come from the inherited interface.
        private final StringCreator nonNullString = new StringCreator(0, 4, 1, false);
        private final StringCreator nonNullField1to10 = new StringCreator(1, 20, 2, false);

        public Builder() {
            mIncrementer = new ObjectCreatorIncrementer(nonNullString, nonNullField1to10);
        }

        @Override
        public Collection<InheritFrom> getValidCases() {
            while (mIncrementer.hasValidPermutations()) {
                validModels.add(new InheritFrom(nonNullString.getValidItem(), nonNullField1to10.getValidItem()));
                mIncrementer.incrementValidCreators();
            }
            return validModels;
        }

        @Override
        public Collection<InheritFrom> getInvalidCases() {
            while (mIncrementer.hasInvalidPermutations()) {
                invalidModels.add(new InheritFrom(nonNullString.getInvalidItem(), nonNullField1to10.getInvalidItem()));
                mIncrementer.incrementInvalidCreators();
            }
            return invalidModels;
        }
    }
}
