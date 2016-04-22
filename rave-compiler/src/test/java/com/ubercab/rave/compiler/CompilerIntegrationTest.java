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

package com.ubercab.rave.compiler;

import com.ubercab.rave.InvalidModelException;
import com.ubercab.rave.model.ArrayNotNull;
import com.ubercab.rave.model.FloatRangeTestModel;
import com.ubercab.rave.model.InheritFrom;
import com.ubercab.rave.model.IntDefModel;
import com.ubercab.rave.model.IntRangeTestModel;
import com.ubercab.rave.model.MultiMethodSampleModel;
import com.ubercab.rave.Rave;
import com.ubercab.rave.RaveException;
import com.ubercab.rave.model.SingleMethodSampleModel;

import org.junit.Test;

import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class CompilerIntegrationTest {

    @Test
    public void allValidSingleMethodSampleModels_shouldPass() throws RaveException {
        SingleMethodSampleModel.Builder builder = new SingleMethodSampleModel.Builder();
        Rave rave = Rave.getInstance();
        Collection<SingleMethodSampleModel> valid = builder.getValidCases();
        assertFalse(valid.isEmpty());
        for (SingleMethodSampleModel model : valid) {
            rave.validate(model);
        }
    }

    @Test
    public void allInvalidSingleMethodSampleModels_shouldFail() throws RaveException {
        SingleMethodSampleModel.Builder builder = new SingleMethodSampleModel.Builder();
        Rave rave = Rave.getInstance();
        Collection<SingleMethodSampleModel> invalid = builder.getInvalidCases();
        assertFalse(invalid.isEmpty());
        for (SingleMethodSampleModel model : invalid) {
            try {
                rave.validate(model);
            } catch (InvalidModelException e) {
                continue;
            }
            assertTrue("Model should have failed with string:\"" + model.getNotNullField() + "\"", false);
        }
    }

    @Test
    public void allValidMultiMethodSampleModels_shouldSucceed() throws RaveException {
        MultiMethodSampleModel.Builder builder = new MultiMethodSampleModel.Builder();
        Collection<MultiMethodSampleModel> validCases = builder.getValidCases();
        assertFalse(validCases.isEmpty());
        Rave rave = Rave.getInstance();
        for (MultiMethodSampleModel model : validCases) {
            rave.validate(model);
        }
    }

    @Test
    public void allInvalidMultiMethodSampleModels_shouldFail() throws RaveException {
        MultiMethodSampleModel.Builder builder = new MultiMethodSampleModel.Builder();
        Collection<MultiMethodSampleModel> invalid = builder.getInvalidCases();
        assertFalse(invalid.isEmpty());
        Rave rave = Rave.getInstance();
        for (MultiMethodSampleModel model : invalid) {
            try {
                rave.validate(model);
            } catch (InvalidModelException e) {
                continue;
            }
            assertTrue(MultiMethodSampleModel.class.getName() + " model should have failed.\n" + model.toString(),
                    false);
        }
    }

    @Test
    public void allValidInheritFromModels_shouldSucceed() throws RaveException {
        InheritFrom.Builder builder = new InheritFrom.Builder();
        Rave rave = Rave.getInstance();
        Collection<InheritFrom> validCases = builder.getValidCases();
        assertFalse(validCases.isEmpty());
        for (InheritFrom model : validCases) {
            rave.validate(model);
        }
    }

    @Test
    public void allInvalidInheritFromModels_shouldFail() throws RaveException {
        InheritFrom.Builder builder = new InheritFrom.Builder();
        Rave rave = Rave.getInstance();
        Collection<InheritFrom> invalidCases = builder.getInvalidCases();
        assertFalse(invalidCases.isEmpty());
        for (InheritFrom model : invalidCases) {
            try {
                rave.validate(model);
            } catch (InvalidModelException e) {
                continue;
            }
            assertTrue("Model should have failed." + model.toString(), false);
        }
    }

    @Test
    public void allValidArrayNotNullModels_shouldSucceed() throws RaveException {
        ArrayNotNull.Builder builder = new ArrayNotNull.Builder();
        Rave rave = Rave.getInstance();
        Collection<ArrayNotNull> valid = builder.getValidCases();
        assertFalse(valid.isEmpty());
        for (ArrayNotNull model : valid) {
            rave.validate(model);
        }
    }

    @Test
    public void allInvalidArrayNotNullModels_shouldFail() throws RaveException {
        ArrayNotNull.Builder builder = new ArrayNotNull.Builder();
        Rave rave = Rave.getInstance();
        Collection<ArrayNotNull> invalid = builder.getInvalidCases();
        assertFalse(invalid.isEmpty());
        for (ArrayNotNull model : invalid) {
            try {
                rave.validate(model);
            } catch (InvalidModelException e) {
                continue;
            }
            assertTrue("Model should have failed." + model.toString(), false);
        }
    }

    @Test
    public void allValidIntDefModel_shouldSucceed() throws RaveException {
        IntDefModel.Builder builder = new IntDefModel.Builder();
        Collection<IntDefModel> validCases = builder.getValidCases();
        assertFalse(validCases.isEmpty());
        Rave rave = Rave.getInstance();
        for (IntDefModel model : validCases) {
            rave.validate(model);
        }
    }

    @Test
    public void allInvalidIntDefModel_shouldFail() throws RaveException {
        IntDefModel.Builder builder = new IntDefModel.Builder();
        Collection<IntDefModel> validCases = builder.getInvalidCases();
        assertFalse(validCases.isEmpty());
        Rave rave = Rave.getInstance();
        for (IntDefModel model : validCases) {
            try {
                rave.validate(model);
            } catch (InvalidModelException e) {
                continue;
            }
            assertTrue("Model should have failed with int: " + model.getStandard() + "\n", false);
        }
    }

    @Test
    public void allValidIntRangeModel_shouldSucceed() throws RaveException {
        IntRangeTestModel.Builder builder = new IntRangeTestModel.Builder();
        Collection<IntRangeTestModel> validCases = builder.getValidCases();
        assertFalse(validCases.isEmpty());
        Rave rave = Rave.getInstance();
        for (IntRangeTestModel model : validCases) {
            rave.validate(model);
        }
    }

    @Test
    public void allInvalidIntRangeModel_shouldFail() throws RaveException {
        IntRangeTestModel.Builder builder = new IntRangeTestModel.Builder();
        Collection<IntRangeTestModel> validCases = builder.getInvalidCases();
        assertFalse(validCases.isEmpty());
        Rave rave = Rave.getInstance();
        for (IntRangeTestModel model : validCases) {
            try {
                rave.validate(model);
            } catch (InvalidModelException e) {
                continue;
            }
            assertTrue("Model should have failed with value: " + model.getValue() + "\n", false);
        }
    }

    @Test
    public void allValidFloatRangeModel_shouldSucceed() throws RaveException {
        FloatRangeTestModel.Builder builder = new FloatRangeTestModel.Builder();
        Collection<FloatRangeTestModel> validCases = builder.getValidCases();
        assertFalse(validCases.isEmpty());
        Rave rave = Rave.getInstance();
        for (FloatRangeTestModel model : validCases) {
            rave.validate(model);
        }
    }

    @Test
    public void allInvalidFloatRangeModel_shouldFail() throws RaveException {
        FloatRangeTestModel.Builder builder = new FloatRangeTestModel.Builder();
        Collection<FloatRangeTestModel> validCases = builder.getInvalidCases();
        assertFalse(validCases.isEmpty());
        Rave rave = Rave.getInstance();
        for (FloatRangeTestModel model : validCases) {
            try {
                rave.validate(model);
            } catch (InvalidModelException e) {
                continue;
            }
            assertTrue("Model should have failed with value: " + model.getValue() + "\n", false);
        }
    }
}
