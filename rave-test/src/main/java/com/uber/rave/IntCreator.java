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

import java.util.HashSet;
import java.util.Set;

/**
 * This class is creates valid and invalid permutations of a long argument based on the annotation
 * restrictions.
 */
public final class IntCreator extends ObjectCreator<Integer> {

    Set<Integer> validValues;

    public IntCreator(int... validValues) {
        this(new AnnotationSpecs.Builder().setIntDef(validValues).build());
    }

    public IntCreator(AnnotationSpecs spec) {
        validValues = new HashSet<>(spec.getValidIntValues().length);
        createValues(spec);
    }

    private void createValues(AnnotationSpecs spec) {
        if (spec.hasIntDef()) {
            createDefValues(spec);
            return;
        }
        if (spec.hasIntRange()) {
            createIntRangeValues(spec);
            return;
        }
    }

    private void createIntRangeValues(AnnotationSpecs spec) {
        addValidType(spec.getRangeFrom());
        addValidType(spec.getRangeTo());
        addValidType((spec.getRangeTo() - spec.getRangeFrom()) / 2 + spec.getRangeFrom());
        boolean hasInvalid = false;
        if (spec.getRangeTo() < Long.MAX_VALUE) {
            hasInvalid = true;
            addInvalidType(spec.getRangeTo() + 1);
        }
        if (spec.getRangeTo() > Long.MIN_VALUE) {
            hasInvalid = true;
            addInvalidType(spec.getRangeFrom() - 1);
        }
        if (!hasInvalid) {
            throw new IllegalArgumentException("No possible invalid values.");
        }
    }

    private void createDefValues(AnnotationSpecs spec) {
        for (int value : spec.getValidIntValues()) {
            addValidType(value);
            validValues.add(value);
        }
        int invalidValues = 0;
        boolean plusMinus = true;
        for (int value : spec.getValidIntValues()) {
            value += (plusMinus) ? -1 : 1;
            plusMinus = !plusMinus;
            if (!validValues.contains(value)) {
                addInvalidType(value);
                invalidValues++;
            }
        }
        int badValue = -1;
        if (invalidValues == 0) {
            while (invalidValues < 1) {
                if (!validValues.contains(badValue)) {
                    addInvalidType(badValue);
                    invalidValues++;
                    badValue++;
                }
            }
        }
    }
}
