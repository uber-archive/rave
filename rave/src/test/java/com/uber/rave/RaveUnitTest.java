// Copyright (c) 2016 Uber Technologies, Inc.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.uber.rave;

import androidx.annotation.NonNull;

import com.uber.rave.model.IntDefModel;
import com.uber.rave.model.LongDefModel;
import com.uber.rave.model.NonAnnotated;
import com.uber.rave.model.SingleMethodSampleModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.common.truth.Truth.assertThat;

public class RaveUnitTest {

    @Test
    public void validateIgnore_whenUsingConstructor_shouldHaveExpectedBehavior() {
        ValidationIgnore v = new ValidationIgnore(String.class);
        assertThat(v.hasIgnoreMethods()).isFalse();
        assertThat(v.shouldIgnoreMethod("foo")).isFalse();
        v.addMethod("foo");
        assertThat(v.hasIgnoreMethods()).isTrue();
        assertThat(v.shouldIgnoreMethod("foo")).isTrue();
    }

    @Test(expected = IllegalStateException.class)
    public void registerValidator_whenTwoValidatorsOnSameModel_shouldFailwithIllegalStateException() {
        TestValidator testValidator = new TestValidator();
        Rave.getInstance().registerValidator(testValidator, testValidator.getSupportedClasses());
        Rave.getInstance().registerValidator(testValidator, testValidator.getSupportedClasses());
    }

    @Test
    public void checkMustBeFalse_whenValidBoolean_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Object.class);
        List<RaveError> errors = BaseValidator.mustBeFalse(false, context);
        assertThat(errors).isNull();
    }

    @Test
    public void checkisSizeOk_whenElementIsNull_shouldNotProduceErrors() {
        Object[] objects = new Objects[1];
        objects[0] = null;
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Object.class);
        List<RaveError> errors = BaseValidator.isSizeOk(objects, true, 1, 1, 1, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableArray_whenCollectionIsNull_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Integer[].class);
        List<RaveError> errors = BaseValidator.checkNullable((Integer[]) null, true, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableArray_whenCollectionIsNotNull_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Integer[].class);
        List<RaveError> errors = BaseValidator.checkNullable(new Integer[0], false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableArray_whenCollectionIsNotNullAndIsNullable_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Integer.class);
        List<RaveError> errors = BaseValidator.checkNullable(new Integer[0], true, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableArray_whenCollectionIsNullButShouldntBe_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Integer.class);
        List<RaveError> errors = BaseValidator.checkNullable((Integer[]) null, false, context);
        assertThat(errors).isNotNull();
        RaveError expectedError = new RaveError(context, RaveErrorStrings.NON_NULL_ERROR);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).toString()).isEqualTo(expectedError.toString());
    }

    @Test
    public void checkNullableArray_whenCollectionHasInvalidObject_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(SingleMethodSampleModel.class);
        context.setValidatedItemName("getNotNullField()");
        SingleMethodSampleModel[] array = new SingleMethodSampleModel[1];
        array[0] = new SingleMethodSampleModel(null, SingleMethodSampleModel.MATCHED1);
        List<RaveError> errors = BaseValidator.checkNullable(array, false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).hasSize(1);
        RaveError expectedError = new RaveError(context, RaveErrorStrings.NON_NULL_ERROR);
        assertThat(errors.get(0).toString()).isEqualTo(expectedError.toString());
    }

    @Test
    public void checkNullableArray_whenCollectionHas2InvalidObjects_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(SingleMethodSampleModel.class);
        SingleMethodSampleModel[] array = new SingleMethodSampleModel[2];
        array[0] = new SingleMethodSampleModel(null, SingleMethodSampleModel.MATCHED1);
        array[1] = new SingleMethodSampleModel("lengthiseven", "Not matching the specified string defs");
        List<RaveError> errors = BaseValidator.checkNullable(array, false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).hasSize(2);
    }

    @Test
    public void checkNullableMap_whenMapIsNull_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(HashMap.class);
        List<RaveError> errors = BaseValidator.checkNullable((Map<Object, Object>) null, true, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkMapCollection_whenMapIsNotNull_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(HashMap.class);
        List<RaveError> errors = BaseValidator.checkNullable(new HashMap<>(), false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableMap_whenMapIsNotNullAndIsNullable_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(HashMap.class);
        List<RaveError> errors = BaseValidator.checkNullable(new HashMap<>(), true, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableMap_whenMapIsNullButShouldntBe_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Map.class);
        List<RaveError> errors = BaseValidator.checkNullable((Map<Object, Object>) null, false, context);
        assertThat(errors).isNotNull();
        RaveError expectedError = new RaveError(context, RaveErrorStrings.NON_NULL_ERROR);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).toString()).isEqualTo(expectedError.toString());
    }

    @Test
    public void checkNullableMap_whenMapHasInvalidObjectAsKey_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(SingleMethodSampleModel.class);
        context.setValidatedItemName("getNotNullField()");
        Map<SingleMethodSampleModel, Object> map = new HashMap<>();
        map.put(new SingleMethodSampleModel(null, SingleMethodSampleModel.MATCHED1), new Object());
        List<RaveError> errors = BaseValidator.checkNullable(map, false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).hasSize(1);
        RaveError expectedError = new RaveError(context, RaveErrorStrings.NON_NULL_ERROR);
        assertThat(errors.get(0).toString()).isEqualTo(expectedError.toString());
    }

    @Test
    public void checkNullableMap_whenMapHasInvalidObjectAsValue_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(SingleMethodSampleModel.class);
        context.setValidatedItemName("getNotNullField()");
        Map<Object, SingleMethodSampleModel> map = new HashMap<>();
        map.put(new Object(), new SingleMethodSampleModel(null, SingleMethodSampleModel.MATCHED1));
        List<RaveError> errors = BaseValidator.checkNullable(map, false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).hasSize(1);
        RaveError expectedError = new RaveError(context, RaveErrorStrings.NON_NULL_ERROR);
        assertThat(errors.get(0).toString()).isEqualTo(expectedError.toString());
    }

    @Test
    public void checkNullableMap_whenMapKeyandValueAreInvalidObjects_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(SingleMethodSampleModel.class);
        context.setValidatedItemName("getNotNullField()");
        SingleMethodSampleModel validSingle = new SingleMethodSampleModel("ab", SingleMethodSampleModel.MATCHED1);
        Map<SingleMethodSampleModel, SingleMethodSampleModel> map = new HashMap<>();
        map.put(validSingle, new SingleMethodSampleModel(null, SingleMethodSampleModel.MATCHED1));
        map.put(new SingleMethodSampleModel("lengthiseven", "Not matching the specified string defs"), validSingle);
        List<RaveError> errors = BaseValidator.checkNullable(map, false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).hasSize(2);
    }

    @Test
    public void checkNullableCollection_whenCollectionIsNull_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Collection.class);
        List<RaveError> errors = BaseValidator.checkNullable((Collection<Object>) null, true, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableCollection_whenCollectionIsNotNull_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Collection.class);
        List<RaveError> errors = BaseValidator.checkNullable(new LinkedList<>(), false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableCollection_whenCollectionIsNotNullAndIsNullable_shouldNotProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Collection.class);
        List<RaveError> errors = BaseValidator.checkNullable(new LinkedList<>(), true, context);
        assertThat(errors).isNotNull();
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkNullableCollection_whenCollectionIsNullButShouldntBe_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Collection.class);
        List<RaveError> errors = BaseValidator.checkNullable((Collection<Object>) null, false, context);
        assertThat(errors).isNotNull();
        RaveError expectedError = new RaveError(context, RaveErrorStrings.NON_NULL_ERROR);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).toString()).isEqualTo(expectedError.toString());
    }

    @Test
    public void checkNullableCollection_whenCollectionHasInvalidObject_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(SingleMethodSampleModel.class);
        context.setValidatedItemName("getNotNullField()");
        List<SingleMethodSampleModel> list = new LinkedList<>();
        list.add(new SingleMethodSampleModel(null, SingleMethodSampleModel.MATCHED1));
        List<RaveError> errors = BaseValidator.checkNullable(list, false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).hasSize(1);
        RaveError expectedError = new RaveError(context, RaveErrorStrings.NON_NULL_ERROR);
        assertThat(errors.get(0).toString()).isEqualTo(expectedError.toString());
    }

    @Test
    public void checkNullableCollection_whenCollectionHas2InvalidObjects_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(SingleMethodSampleModel.class);
        List<SingleMethodSampleModel> list = new LinkedList<>();
        list.add(new SingleMethodSampleModel(null, SingleMethodSampleModel.MATCHED1));
        list.add(new SingleMethodSampleModel("lengthiseven", "Not matching the specified string defs"));
        List<RaveError> errors = BaseValidator.checkNullable(list, false, context);
        assertThat(errors).isNotNull();
        assertThat(errors).hasSize(2);
    }

    @Test
    public void checkMustBeFalse_whenInvalidBoolean_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Object.class);
        context.setValidatedItemName("someMethodName");
        List<RaveError> errors = BaseValidator.mustBeFalse(true, context);
        assertThat(errors).isNotNull();
        RaveError expectedError = new RaveError(context, RaveErrorStrings.MUST_BE_FALSE_ERROR);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).toString()).isEqualTo(expectedError.toString());
    }

    @Test
    public void checkString_whenCollectionIsValid_shouldNotProduceErrors() {
        Collection<String> strings = new ArrayList<>();
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Object.class);
        strings.add("Valid1");
        strings.add("Valid2");
        strings.add("Valid3");
        List<RaveError> errors = BaseValidator.checkStringDef(true, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).isEmpty();
        errors = BaseValidator.checkStringDef(false, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkString_whenCollectionIsInalid_shouldProduceErrors() {

        Collection<String> strings = new ArrayList<>();
        strings.add("Valid1");
        strings.add("InValid2");
        strings.add("Valid3");
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Object.class);
        List<RaveError> errors = BaseValidator.checkStringDef(false, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getErrorMsg()).contains(RaveErrorStrings.STRING_DEF_ERROR);
        errors = BaseValidator.checkStringDef(true, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getErrorMsg()).contains(RaveErrorStrings.STRING_DEF_ERROR);
    }

    @Test
    public void checkString_whenCollectionEmptyOrNull_shouldNotProduceErrors() {
        Collection<String> strings = new ArrayList<>();
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Object.class);
        List<RaveError> errors = BaseValidator.checkStringDef(false, context, strings);
        assertThat(errors).isEmpty();
        errors = BaseValidator.checkStringDef(true, context, (Collection<String>) null);
        assertThat(errors).isEmpty();
        errors = BaseValidator.checkStringDef(true, context, (Collection<String>) null, "Valid");
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkString_whenArrayIsValid_shouldNotProduceErrors() {
        String[] strings = new String[] {"Valid1", "Valid2", "Valid3"};
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(Object.class);
        List<RaveError> errors = BaseValidator.checkStringDef(true, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).isEmpty();
        errors = BaseValidator.checkStringDef(false, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkString_whenArrayIsNullOrEmpty_shouldNotProduceErrors() {
        String[] strings = new String[0];
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        List<RaveError> errors = BaseValidator.checkStringDef(false, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).isEmpty();
        errors = BaseValidator.checkStringDef(true, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).isEmpty();
        errors = BaseValidator.checkStringDef(true, context, (String[]) null, "Valid1", "Valid2", "Valid3");
        assertThat(errors).isEmpty();
        errors = BaseValidator.checkStringDef(true, context, (String[]) null);
        assertThat(errors).isEmpty();
    }

    @Test
    public void checkString_whenStringIsNull_shouldProduceErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        List<RaveError> errors = BaseValidator.checkStringDef(false, context, (String) null, "Valid1", "Valid2",
                "Valid3");
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).toString().contains(RaveErrorStrings.NON_NULL_ERROR)).isTrue();
    }

    @Test
    public void checkString_whenStringIsNotNull_shouldProduceNoErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        List<RaveError> errors = BaseValidator.checkStringDef(true, context, (String) null,
                "Valid1", "Valid2", "Valid3");
        assertThat(errors).hasSize(0);

    }

    @Test
    public void checkString_whenArrayIsInValid_shouldProduceError() {
        String[] strings = new String[] {"Valid1", "Invalid2", "Valid3"};
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        List<RaveError> errors = BaseValidator.checkStringDef(true, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).toString()).contains(RaveErrorStrings.STRING_DEF_ERROR);
        errors = BaseValidator.checkStringDef(false, context, strings, "Valid1", "Valid2", "Valid3");
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).toString()).contains(RaveErrorStrings.STRING_DEF_ERROR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateAs_whenClazzTypeDoesntMatchObjectType_shouldFailWithException() throws RaveException {
        Rave.getInstance().validateAs(new Object(), String.class);
    }

    @Test(expected = UnsupportedObjectException.class)
    public void validateAs_whenTypeIsNotSupported_shouldFailWithRaveUnsupportedException() throws RaveException {
        Rave.getInstance().validateAs(new Object(), Object.class);
    }

    @Test
    public void validateIgnoreUnsupported_whenTypeIsNotSupported_shouldNotFailWithRaveUnsupportedException()
            throws RaveException {
        Rave.getInstance().validateIgnoreUnsupported(new Object());
    }

    @Test(expected = UnsupportedObjectException.class)
    public void validate_whenTypeIsNotSupported_shouldFailWithRaveUnsupportedException() throws RaveException {
        Rave.getInstance().validate(new Object());
    }

    @Test
    public void exceptionMessage_whenObjectNotSupported_shouldThrowObjectNotSupportedError() throws RaveException {
        try {
            Rave.getInstance().validate(new Object());
        } catch (UnsupportedObjectException e) {
            RaveError er = new RaveError(Object.class, "", RaveErrorStrings.CLASS_NOT_SUPPORTED_ERROR);
            assertThat(er.toString() + "\n").isEqualTo(e.getMessage());
            Iterator<RaveError> iter = e.getRaveErrorIterator();
            assertThat(iter.hasNext()).isTrue();
            RaveError error = iter.next();
            assertThat(er.toString()).isEqualTo(error.toString());
            assertThat(iter.hasNext()).isFalse();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void validate_whenTryToValidateUnsupportedClass_shouldThrowIllegalArgException() throws RaveException {
        TestValidator testValidator = new TestValidator();
        testValidator.validate(new Object());
    }

    @Test(expected = UnsupportedObjectException.class)
    public void unAnnotateValidatorHandlerValidateAs_whenUnValidateUnsupportedClass_shouldThrowException()
            throws RaveException {
        Rave.getInstance().validate(new NonAnnotated.UnAnnotatedEvenWithInheritance());
    }

    @Test
    public void unAnnotateValidatorHandlerValidateAs_whenUnValidateUnsupportedClass_shouldNotThrowException()
            throws RaveException {
        Rave.getInstance().validateIgnoreUnsupported(new NonAnnotated.UnAnnotatedEvenWithInheritance());
    }

    @Test
    public void unAnnotateValidatorHandlerValidateAs_whenInheritenceIsUsed_shouldValidateFine() throws RaveException {
        Rave.getInstance().validate(new NonAnnotated(""));
        Rave.getInstance().validate(new NonAnnotated(""));
    }

    @Test(expected = InvalidModelException.class)
    public void unAnnotateValidatorHandlerValidate_whenModelInvalid_shouldThrowInvalidModelException()
            throws RaveException {
        Rave.getInstance().validate(new NonAnnotated(null));
    }

    @Test(expected = UnsupportedObjectException.class)
    public void validate_whenNonSupportedClass_shouldThrowException() throws RaveException {
        Rave.getInstance().validate(Collections.emptyList());
    }

    @Test
    public void validate_whenNonSupportedClass_shouldNotThrowException() throws RaveException {
        Rave.getInstance().validateIgnoreUnsupported(Collections.emptyList());
    }

    @Test(expected = UnsupportedObjectException.class)
    public void unAnnotateValidatorHandlerProcessAnnotation_whenUnSupportedClass_shouldThrowUnsupportedObjectException()
            throws RaveException {
        Rave.UnAnnotatedModelValidator handler = new Rave.UnAnnotatedModelValidator(1);
        handler.processNonAnnotatedClasses(Object.class);
        handler.processNonAnnotatedClasses(String.class);
        handler.validateAs("", String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unAnnotateValidatorHandlerProcessAnnotation_whenUnSupportedClass_shouldThrowException()
            throws RaveException {
        Rave.UnAnnotatedModelValidator handler = new Rave.UnAnnotatedModelValidator(1);
        handler.processNonAnnotatedClasses(Object.class);
        handler.processNonAnnotatedClasses(String.class);
        handler.validateAs(new Object(), Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unAnnotateValidatorHandlerValidateAs_whenNonSupportedClass_shouldThrowException() throws RaveException {
        Rave.UnAnnotatedModelValidator handler = new Rave.UnAnnotatedModelValidator(1);
        handler.validateAs(new NonAnnotated(""), NonAnnotated.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unAnnotateValidatorHandlerValidateAs_whenModelIsAnnotated_shouldThrowException() throws RaveException {
        Rave.UnAnnotatedModelValidator handler = new Rave.UnAnnotatedModelValidator(1);
        handler.validateAs(new SingleMethodSampleModel("", ""), SingleMethodSampleModel.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void processNonAnnotatedClass_whenModelIsAnnotated_shouldThrowException() throws RaveException {
        Rave.UnAnnotatedModelValidator handler = new Rave.UnAnnotatedModelValidator(1);
        handler.processNonAnnotatedClasses(SingleMethodSampleModel.class);
    }

    @Test
    public void checkIntDef_whenValueIsCorrect_shouldProduceNoErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        assertThat(BaseValidator.checkIntDef(context, 10, false, 10).isEmpty());
    }

    @Test
    public void checkIntDef_whenValueMatchesSet_shouldProduceNoErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        assertThat(BaseValidator.checkIntDef(context, 10, false, 10, 11, 12, 13).isEmpty());
    }

    @Test
    public void checkIntDef_whenValueDoesNotMatch_shouldProduceError() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        List<RaveError> errors = BaseValidator.checkIntDef(context, 9, false, 10, 11, 12, 13);
        assertThat(errors).isNotEmpty();
        assertThat(errors).hasSize(1);
        RaveError error = errors.get(0);
        assertThat(error.getErrorMsg()).contains(RaveErrorStrings.INT_DEF_ERROR);
    }

    @Test
    public void intDefModel_whenModelIsValid_shouldNotThrowException() throws RaveException {
        IntDefModel model = new IntDefModel(IntDefModel.NAVIGATION_MODE_LIST);
        Rave.getInstance().validate(model);
    }

    @Test
    public void checkLongDef_whenValueIsCorrect_shouldProduceNoErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        assertThat(BaseValidator.checkLongDef(context, 10L, false, 10L).isEmpty());
    }

    @Test
    public void checkLongDef_whenValueMatchesSet_shouldProduceNoErrors() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        assertThat(BaseValidator.checkLongDef(context, 10L, false, 10L, 11L, 12L, 13L).isEmpty());
    }

    @Test
    public void checkLongDef_whenValueDoesNotMatch_shouldProduceError() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        List<RaveError> errors = BaseValidator.checkLongDef(context, 9L, false, 10L, 11L, 12L, 13L);
        assertThat(errors).isNotEmpty();
        assertThat(errors).hasSize(1);
        RaveError error = errors.get(0);
        assertThat(error.getErrorMsg()).contains(RaveErrorStrings.LONG_DEF_ERROR);
    }

    @Test
    public void longDefModel_whenModelIsValid_shouldNotThrowException() throws RaveException {
        LongDefModel model = new LongDefModel(LongDefModel.NAVIGATION_MODE_LIST);
        Rave.getInstance().validate(model);
    }

    @Test
    public void checkIntRange_whenValuesInRange_shouldReturnEmptyList() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        assertThat(BaseValidator.checkIntRange(context, 10, -1, 10).isEmpty()).isTrue();
        assertThat(BaseValidator.checkIntRange(context, 10, -1, 11).isEmpty()).isTrue();
        assertThat(BaseValidator.checkIntRange(context, -1, -1, 11).isEmpty()).isTrue();
    }

    @Test
    public void checkIntRange_whenValuesNotInRange_shouldReturnError() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        assertThat(BaseValidator.checkIntRange(context, -10, -1, 10)).isNotEmpty();
        assertThat(BaseValidator.checkIntRange(context, 12, -1, 11)).isNotEmpty();
        List<RaveError> errors = BaseValidator.checkIntRange(context, 12, -1, 11);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getErrorMsg()).contains(RaveErrorStrings.INT_RANGE_ERROR);
    }

    @Test
    public void checkIntRange_whenValuesInFloatRange_shouldReturnEmptyList() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        assertThat(BaseValidator.checkFloatRange(context, 10d, -1.0d, 10d)).isEmpty();
        assertThat(BaseValidator.checkFloatRange(context, 10d, -1.0d, 11d)).isEmpty();
        assertThat(BaseValidator.checkFloatRange(context, -1d, -1d, 11d)).isEmpty();
        assertThat(BaseValidator.checkFloatRange(context, -1d, Double.NEGATIVE_INFINITY, 11d)).isEmpty();
        assertThat(BaseValidator.checkFloatRange(context, -1d, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY))
                .isEmpty();
        assertThat(BaseValidator.checkFloatRange(context, -1d, -10d, Double.POSITIVE_INFINITY)).isEmpty();
        assertThat(BaseValidator.checkFloatRange(context, Double.POSITIVE_INFINITY, -10d, Double.POSITIVE_INFINITY))
                .isEmpty();
        assertThat(BaseValidator.checkFloatRange(context, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY)).isEmpty();
    }

    @Test
    public void checkIntRange_whenValuesNotInFloatRange_shouldReturnError() {
        BaseValidator.ValidationContext context = BaseValidator.getValidationContext(String.class);
        assertThat(BaseValidator.checkFloatRange(context, -10d, -1d, 10d)).isNotEmpty();
        assertThat(BaseValidator.checkFloatRange(context, 12d, -1d, 11d)).isNotEmpty();
        List<RaveError> errors = BaseValidator.checkFloatRange(context, 12d, -1d, 11d);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getErrorMsg()).contains(RaveErrorStrings.FLOAT_RANGE_ERROR);
    }

    @Test(expected = InvalidModelException.class)
    public void intDefModel_whenModelIsInvalid_shouldThrowException() throws RaveException {
        IntDefModel model = new IntDefModel(1111111111);
        Rave.getInstance().validate(model);
    }

    public static class TestValidator extends BaseValidator {

        public TestValidator() {
            addSupportedClass(String.class);
        }

        @Override
        protected void validateAs(
                @NonNull Object obj, @NonNull Class<?> clazz)
                throws RaveException {

        }

    }
}
