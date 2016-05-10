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

/**
 * This class is creates valid and invalid permutations of a String argument based on the annotation
 * restrictions.
 */
public class StringCreator extends ObjectCreator<String> {

    private static final String BASE_STRING = "ABC123 @#$%asdfmh";

    /**
     * @return a default {@link StringCreator} with no restrictions.
     */
    public static StringCreator getBasicStringCreator() {
        return new StringCreator();
    }

    private StringCreator() {
        addValidType(BASE_STRING);
    }

    public StringCreator(AnnotationSpecs spec, StringInput... stringInputs) {
        createStrings(spec);
        for (StringInput input : stringInputs) {
            if (input.isValid) {
                addValidType(input.string);
            } else {
                addInvalidType(input.string);
            }
        }
    }

    public StringCreator(long min, long max, long multiple, boolean isNullable, StringInput... stringInputs) {
        this(new AnnotationSpecs.Builder().setIsNullable(isNullable).setSize(min, max, multiple).build(), stringInputs);
    }

    public StringCreator(boolean isNullable, StringInput... stringInputs) {
        this(new AnnotationSpecs.Builder().setIsNullable(isNullable).build(), stringInputs);
    }

    private void createStrings(AnnotationSpecs spec) {
        if (spec.isNullable()) {
            addValidType(null);
        } else {
            addInvalidType(null);
        }
        if (spec.hasStringDef()) {
            for (String s : spec.getValidStrings()) {
                addValidType(s);
            }
            addInvalidType(BASE_STRING);
            return;
        }
        if (spec.getSizeMax() >= 0) {
            String baseString = BASE_STRING;
            while (baseString.length() < spec.getSizeMax() + 2) {
                baseString += BASE_STRING;
            }
            if (spec.getSizeMax() > Integer.MAX_VALUE || spec.getSizeMultiple() > Integer.MAX_VALUE) {
                throw new IllegalArgumentException(
                        "For testing size max and multiple for the size annotation must be greater than "
                                + "Integer.MaxValue");
            }
            int maxSize = Utils.roundDown(spec.getSizeMax(), spec.getSizeMultiple());
            if (maxSize >= spec.getSizeMin()) {
                addValidType(baseString.substring(0, maxSize));
            }
            int minSize = Utils.roundUp(spec.getSizeMin(), spec.getSizeMultiple());
            if (minSize <= spec.getSizeMax()) {
                addValidType(baseString.substring(0, minSize));
            }
            if (spec.getSizeMultiple() == 1) {
                long middle = (spec.getSizeMax() + spec.getSizeMin()) / 2;
                addValidType(baseString.substring(0, (int) middle));
            }
            addInvalidType(baseString.substring(0, (int) spec.getSizeMax() + 1));
            if (spec.getSizeMin() > 0) {
                addInvalidType(baseString.substring(0, (int) spec.getSizeMin() - 1));
            }
            if (spec.getSizeMultiple() > 1 && maxSize > spec.getSizeMin()) {
                int nonMutliple = maxSize + 1;
                addInvalidType(baseString.substring(0, nonMutliple));
            }
        } else {
            addValidType(BASE_STRING);
        }
    }

    /**
     * This class represents any extra inputs that a developer would like to add to the {@link StringCreator}.
     */
    public static class StringInput {

        public final boolean isValid;
        public final String string;

        public StringInput(boolean isValid, String string) {
            this.isValid = isValid;
            this.string = string;
        }
    }
}
