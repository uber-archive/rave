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
 * The error messages produced by validations.
 */
public final class RaveErrorStrings {
    public static final String CLASS_NOT_SUPPORTED_ERROR = "Is not supported by validation.";
    public static final String FLOAT_RANGE_ERROR = "Does not conform to the following @FloatRange values:";
    public static final String NON_NULL_ERROR = "Item is null and shouldn't be.";
    public static final String INT_DEF_ERROR = "Does not conform to the following @IntDef values:";
    public static final String LONG_DEF_ERROR = "Does not conform to the following @LongDef values:";
    public static final String INT_RANGE_ERROR = "Does not conform to the following @IntRange values:";
    public static final String MUST_BE_TRUE_ERROR = "Is not true.";
    public static final String MUST_BE_FALSE_ERROR = "Is not false.";
    public static final String STRING_DEF_ERROR = "Does not conform to the following @StringDef values:";

    private RaveErrorStrings() { }
}
