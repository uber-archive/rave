package com.ubercab.rave.model;

import android.support.annotation.NonNull;

import com.ubercab.rave.BaseValidator;
import com.ubercab.rave.ExclusionStrategy;
import com.ubercab.rave.RaveError;
import com.ubercab.rave.InvalidModelException;

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
        registerSelf();
    }

    @Override
    protected void validateAs(
            @NonNull Object object,
            @NonNull Class<?> clazz,
            @NonNull ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(
                    object.getClass().getCanonicalName() + "is not of type" + clazz.getCanonicalName());
        }
        if (clazz.equals(InheritFrom.class)) {
            validateAs((InheritFrom) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(ValidateByInterface.class)) {
            validateAs((ValidateByInterface) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(MultiMethodSampleModel.class)) {
            validateAs((MultiMethodSampleModel) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(SingleMethodSampleModel.class)) {
            validateAs((SingleMethodSampleModel) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(ArrayNotNull.class)) {
            validateAs((ArrayNotNull) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(AbstractAnnotated.class)) {
            validateAs((AbstractAnnotated) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(IntDefModel.class)) {
            validateAs((IntDefModel) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(IntRangeTestModel.class)) {
            validateAs((IntRangeTestModel) object, exclusionStrategy);
            return;
        }
        if (clazz.equals(FloatRangeTestModel.class)) {
            validateAs((FloatRangeTestModel) object, exclusionStrategy);
            return;
        }
        throw new IllegalArgumentException(
                object.getClass().getCanonicalName() + " is not supported by validator " + this.getClass()
                        .getCanonicalName());
    }

    private void validateAs(InheritFrom object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(InheritFrom.class);
        List<RaveError> raveErrors = null;
        raveErrors = mergeErrors(raveErrors,
                reEvaluateAsSuperType(SingleMethodSampleModel.class, object, exclusionStrategy));
        raveErrors = mergeErrors(raveErrors,
                reEvaluateAsSuperType(ValidateByInterface.class, object, exclusionStrategy));
        if (!setContextAndCheckshouldIgnoreMethod(InheritFrom.class, "getNonNullString", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getNonNullString(), false, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ValidateByInterface object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ValidateByInterface.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(ValidateByInterface.class, "getNonNullString", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNonNullString(), false, 0L, 4L, 1L, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(MultiMethodSampleModel object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(MultiMethodSampleModel.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(MultiMethodSampleModel.class, "getNonAnnotatedObject",
                exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getNonAnnotatedObject(), true, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(MultiMethodSampleModel.class, "getNotNullField", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getNotNullField(), false, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(MultiMethodSampleModel.class, "getCanBeNullField", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.getCanBeNullField(), true, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(MultiMethodSampleModel.class, "getBetweenOneAndFive",
                exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getBetweenOneAndFive(), true, 1L, 5L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(MultiMethodSampleModel.class, "getNames", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNames(), true, 1L, 5L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(MultiMethodSampleModel.class, "getIsFalse", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, mustBeFalse(object.getIsFalse(), context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(MultiMethodSampleModel.class, "getIsTrue", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, mustBeTrue(object.getIsTrue(), context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(SingleMethodSampleModel object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(SingleMethodSampleModel.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(SingleMethodSampleModel.class, "getNotNullField", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getNotNullField(), false, 1L, 20L, 2L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(SingleMethodSampleModel.class, "getMatchStringDef", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, checkStringDef(false, context, object.getMatchStringDef(), "Matched",
                    "Matching", "AlsoMatching"));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(ArrayNotNull object, ExclusionStrategy exclusionStrategy) throws InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(ArrayNotNull.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(ArrayNotNull.class, "getSingles", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getSingles(), false, 1L, 3L, 1L, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(ArrayNotNull.class, "getStringsArray", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, isSizeOk(object.getStringsArray(), false, 5L, 20L, 1L, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(AbstractAnnotated object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(AbstractAnnotated.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(AbstractAnnotated.class, "nonNullAbstractMethodString",
                exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.nonNullAbstractMethodString(), false, context));
        }
        if (!setContextAndCheckshouldIgnoreMethod(AbstractAnnotated.class, "nonNullString", exclusionStrategy,
                context)) {
            raveErrors = mergeErrors(raveErrors, checkNullable(object.nonNullString(), false, context));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(IntDefModel object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(IntDefModel.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(IntDefModel.class, "getStandard", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkIntDef(context, object.getStandard(), false, 0, 1, 2));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(IntRangeTestModel object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(IntRangeTestModel.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(IntRangeTestModel.class, "getValue", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkIntRange(context, object.getValue(), -15L, 1000L));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }

    private void validateAs(FloatRangeTestModel object, ExclusionStrategy exclusionStrategy) throws
            InvalidModelException {
        BaseValidator.ValidationContext context = getValidationContext(FloatRangeTestModel.class);
        List<RaveError> raveErrors = null;
        if (!setContextAndCheckshouldIgnoreMethod(FloatRangeTestModel.class, "getValue", exclusionStrategy, context)) {
            raveErrors = mergeErrors(raveErrors, checkFloatRange(context, object.getValue(), -15.5d, 1000.9d));
        }
        if (raveErrors != null && !raveErrors.isEmpty()) {
            throw new InvalidModelException(raveErrors);
        }
    }
}
