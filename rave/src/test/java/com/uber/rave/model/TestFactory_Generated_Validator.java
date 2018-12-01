package com.uber.rave.model;

import androidx.annotation.NonNull;

import com.uber.rave.BaseValidator;
import com.uber.rave.RaveError;
import com.uber.rave.InvalidModelException;

import java.util.List;

public final class TestFactory_Generated_Validator extends BaseValidator {

    public TestFactory_Generated_Validator() {
        addSupportedClass(InheritFrom.class);
        addSupportedClass(ValidateByInterface.class);
        addSupportedClass(MultiMethodSampleModel.class);
        addSupportedClass(SingleMethodSampleModel.class);
        addSupportedClass(ArrayNotNull.class);
        addSupportedClass(AbstractAnnotated.class);
        addSupportedClass(IntDefModel.class);
        addSupportedClass(IntRangeTestModel.class);
        addSupportedClass(FloatRangeTestModel.class);
        addSupportedClass(LongDefModel.class);
        registerSelf();
    }

    @Override
    protected void validateAs(
            @NonNull Object object,
            @NonNull Class<?> clazz) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(
                    object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(InheritFrom.class)) {
            validateAs((InheritFrom) object);
            return;
        }
        if (clazz.equals(ValidateByInterface.class)) {
            validateAs((ValidateByInterface) object);
            return;
        }
        if (clazz.equals(MultiMethodSampleModel.class)) {
            validateAs((MultiMethodSampleModel) object);
            return;
        }
        if (clazz.equals(SingleMethodSampleModel.class)) {
            validateAs((SingleMethodSampleModel) object);
            return;
        }
        if (clazz.equals(ArrayNotNull.class)) {
            validateAs((ArrayNotNull) object);
            return;
        }
        if (clazz.equals(AbstractAnnotated.class)) {
            validateAs((AbstractAnnotated) object);
            return;
        }
        if (clazz.equals(IntDefModel.class)) {
            validateAs((IntDefModel) object);
            return;
        }
        if (clazz.equals(IntRangeTestModel.class)) {
            validateAs((IntRangeTestModel) object);
            return;
        }
        if (clazz.equals(FloatRangeTestModel.class)) {
            validateAs((FloatRangeTestModel) object);
            return;
        }
        if (clazz.equals(LongDefModel.class)) {
            validateAs((LongDefModel) object);
            return;
        }
        throw new IllegalArgumentException(
                object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass()
                        .getCanonicalName());
    }

    private void validateAs(InheritFrom object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(InheritFrom.class);
        List<RaveError> raveErrors = null;
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(SingleMethodSampleModel.class, object));
        raveErrors = mergeErrors(raveErrors, reEvaluateAsSuperType(ValidateByInterface.class, object));
        context.setValidatedItemName("getNonNullString()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getNonNullString(), false, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateByInterface object) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateByInterface.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNonNullString()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNonNullString(), false, 0L, 4L, 1L, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(MultiMethodSampleModel object) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(MultiMethodSampleModel.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNonAnnotatedObject()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getNonAnnotatedObject(), true, context));
        context.setValidatedItemName("getNotNullField");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getNotNullField(), false, context));
        context.setValidatedItemName("getCanBeNullField()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.getCanBeNullField(), true, context));
        context.setValidatedItemName("getBetweenOneAndFive()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getBetweenOneAndFive(), true, 1L, 5L, 1L, context));
        context.setValidatedItemName("getNames()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNames(), true, 1L, 5L, 1L, context));
        context.setValidatedItemName("getIsFalse()");
        raveErrors = mergeErrors(raveErrors, mustBeFalse(object.getIsFalse(), context));
        context.setValidatedItemName("getIsTrue()");
        raveErrors = mergeErrors(raveErrors, mustBeTrue(object.getIsTrue(), context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(SingleMethodSampleModel object) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SingleMethodSampleModel.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getNotNullField()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNotNullField(), false, 1L, 20L, 2L, context));
        context.setValidatedItemName("getMatchStringDef()");
        raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getMatchStringDef(), "Matched",
                "Matching", "AlsoMatching"));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ArrayNotNull object) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ArrayNotNull.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getSingles()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getSingles(), false, 1L, 3L, 1L, context));
        context.setValidatedItemName("getStringsArray()");
        raveErrors = mergeErrors(raveErrors, isSizeOk(object.getStringsArray(), false, 5L, 20L, 1L, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(AbstractAnnotated object) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(AbstractAnnotated.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("nonNullAbstractMethodString()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.nonNullAbstractMethodString(), false, context));
        context.setValidatedItemName("nonNullString()");
        raveErrors = mergeErrors(raveErrors, checkNullable(object.nonNullString(), false, context));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(IntDefModel object) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(IntDefModel.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getStandard()");
        raveErrors = mergeErrors(raveErrors, checkIntDef(context, object.getStandard(), false, 0, 1, 2));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(LongDefModel object) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(LongDefModel.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getStandard()");
        raveErrors = mergeErrors(raveErrors, checkLongDef(context, object.getStandard(), false, 0L, 1L, 2L));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(IntRangeTestModel object) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(IntRangeTestModel.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getValue()");
        raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getValue(), -15, 1000));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(FloatRangeTestModel object) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(FloatRangeTestModel.class);
        List<RaveError> raveErrors = null;
        context.setValidatedItemName("getValue()");
        raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getValue(), -15.5d, 1000.9d));
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
