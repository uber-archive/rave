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
 * Holds a single Rave error. This generally will be validation failure.
 */
public final class RaveError {

    private final String errorMsg;

    private final String classElement;
    private final Class<?> clazz;

    /**
     * @param clazz the inner most {@link Class} that failed validation.
     * @param item the name of the item that failed validation such as the method name.
     * @param errorMsg the error message.
     */
    public RaveError(Class<?> clazz, String item, String errorMsg) {
        this.clazz = clazz;
        this.errorMsg = errorMsg;
        classElement = item;
    }

    /**
     * @param validationContext the {@link BaseValidator.ValidationContext}.
     * @param errorMsg the error message.
     */
    public RaveError(BaseValidator.ValidationContext validationContext, String errorMsg) {
        this.clazz = validationContext.getClazz();
        classElement = validationContext.getValidatedItemName();
        this.errorMsg = errorMsg;
    }

    /**
     * @return the {@link String} error messaged for this error.
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * This method returns the description of what element in the class failed validation, for example the method name.
     *
     * @return the {@link String} representation of the element in the class.
     */
    public String getClassElement() {
        return classElement;
    }

    /**
     * @return the {@link Class} object.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        if (classElement.isEmpty()) {
            return clazz.getCanonicalName() + ":" + errorMsg;
        }
        return clazz.getCanonicalName() + ":" + classElement + ":" + errorMsg;
    }
}
