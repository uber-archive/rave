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

package com.ubercab.rave;

import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

/**
 * A class designed to hold any annotation information on a method block.
 */
public final class AnnotationSpecs {

    private long sizeMax = -1;
    private long sizeMin = -1;
    private long sizeMultiple = 1;
    private boolean isNullable = true;
    private boolean mustBeTrueIsSet = false;
    private boolean mustBeFalseIsSet = false;
    private boolean hasStringDef = false;
    private String[] validStrings = new String[0];
    private boolean hasIntDef = false;
    private long[] validLongValues = new long[0];
    private boolean hasIntRange = false;
    private long rangeTo = Long.MAX_VALUE;
    private long rangeFrom = Long.MIN_VALUE;
    private boolean hasFloatRange = false;
    private double rangeFloatTo = Double.MAX_VALUE;
    private double rangeFloatFrom = Double.MIN_VALUE;

    private AnnotationSpecs() { }

    public boolean hasFloatRange() {
        return hasFloatRange;
    }

    public double getRangeFloatTo() {
        return rangeFloatTo;
    }

    public double getRangeFloatFrom() {
        return rangeFloatFrom;
    }

    public boolean hasIntRange() {
        return hasIntRange;
    }

    public long getRangeTo() {
        return rangeTo;
    }

    public long getRangeFrom() {
        return rangeFrom;
    }

    public long getSizeMin() {
        return sizeMin;
    }

    public long getSizeMax() {
        return sizeMax;
    }

    public long getSizeMultiple() { return sizeMultiple; }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isMustBeTrueIsSet() {
        return mustBeTrueIsSet;
    }

    public boolean isMustBeFalseIsSet() {
        return mustBeFalseIsSet;
    }

    public boolean hasStringDef() { return hasStringDef; }

    public String[] getValidStrings() {
        return validStrings;
    }

    public boolean hasIntDef() {
        return hasIntDef;
    }

    public long[] getValidLongValues() {
        return validLongValues;
    }

    /**
     * Builder for annotation spec.
     */
    public static class Builder {

        AnnotationSpecs annotationSpecs = new AnnotationSpecs();

        /**
         * Set the {@link } annotation parameters. Indicates that this spec has
         * a size annotation with min and max.
         *
         * @param min the minimum size value seen on the annotation.
         * @param max the maximum size value seen on the annotation.
         * @return this {@link Builder}
         */
        public Builder setSize(long min, long max, long multiple) {
            annotationSpecs.sizeMultiple = multiple;
            annotationSpecs.sizeMin = min;
            annotationSpecs.sizeMax = max;
            return this;
        }

        /**
         * Enables the true annotation flag. This method sets the boolean which
         * indicates the presents of the annotation.
         *
         * @return this {@link Builder}
         */
        public Builder enableMustBeTrue() {
            annotationSpecs.mustBeTrueIsSet = true;
            return this;
        }

        /**
         * Enables the false annotation flag. This method sets the boolean which
         * indicates the presents of the annotation.
         *
         * @return this {@link Builder}
         */
        public Builder enableMustBeFalse() {
            annotationSpecs.mustBeFalseIsSet = true;
            return this;
        }

        /**
         * Sets the {@link Nullable} annotation flag. This method sets the boolean which
         * indicates the presents of the annotation.
         *
         * @param isNullable should be true if this annotation has the {@link Nullable} annotation and false if
         * {@link android.support.annotation.NonNull} is set.
         * @return this {@link Builder}
         */
        public Builder setIsNullable(boolean isNullable) {
            annotationSpecs.isNullable = isNullable;
            return this;
        }

        /**
         * Set the valid values for the strings in a {@link StringDef}.
         *
         * @param strings the valid values.
         * @return this {@link Builder}
         */
        public Builder setStringDef(String... strings) {
            annotationSpecs.hasStringDef = true;
            annotationSpecs.validStrings = strings;
            return this;
        }

        /**
         * Set the valid values for the int in a {@link android.support.annotation.IntDef}.
         *
         * @param values the valid values.
         * @return this {@link Builder}
         */
        public Builder setIntDef(long... values) {
            annotationSpecs.hasIntDef = true;
            annotationSpecs.validLongValues = values;
            return this;
        }

        /**
         * Set the valid range for the int in a {@link android.support.annotation.IntRange}.
         *
         * @param from the lower bound of the range inclusive.
         * @param to the upper bound of the rnage inclusive.
         * @return this {@link Builder}
         */
        public Builder setIntRange(long from, long to) {
            annotationSpecs.hasIntRange = true;
            annotationSpecs.rangeTo = to;
            annotationSpecs.rangeFrom = from;
            return this;
        }

        /**
         * Set the valid values for the doubles in a {@link android.support.annotation.FloatRange}.
         *
         * @param from the lower bound of the range inclusive.
         * @param to the upper bound of the rnage inclusive.
         * @return this {@link Builder}
         */
        public Builder setFloatRange(double from, double to) {
            annotationSpecs.hasFloatRange = true;
            annotationSpecs.rangeFloatTo = to;
            annotationSpecs.rangeFloatFrom = from;
            return this;
        }

        /**
         * Build the {@link AnnotationSpecs}
         *
         * @return the {@link AnnotationSpecs}.
         */
        public AnnotationSpecs build() {
            return annotationSpecs;
        }
    }
}
