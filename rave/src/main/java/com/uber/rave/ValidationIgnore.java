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
 * A container that represents the class or class+method to ignore during Rave validation.
 */
final class ValidationIgnore {
    private final Set<String> ignoreMethods;

    private final Class<?> clazz;

    private boolean ignoreClassAll = false;

    /**
     * Constructor for this ignore bundle.
     * @param clazz the class to ignore.
     */
    ValidationIgnore(Class<?> clazz) {
        ignoreMethods = new HashSet<>();
        this.clazz = clazz;
    }

    /**
     * @param error the error to match
     * @return Returns true if this error matches the criteria of this ignore rule.
     */
    public boolean shouldIgnore(RaveError error) {
        if (!error.getClazz().equals(clazz)) {
            return false;
        }
        return ignoreClassAll || ignoreMethods.contains(error.getClassElement());
    }

    /**
     * @return True if this {@link ValidationIgnore} is for the entire class and not just specific elements.
     */
    boolean isIgnoreClassAll() {
        return ignoreClassAll;
    }

    Class<?> getClazz() {
        return clazz;
    }

    /**
     * Sets this {@link ValidationIgnore} so that the entire class is ignored during validation.
     */
    void setAsIgnoreClassAll() {
        ignoreClassAll = true;
    }

    /**
     * @return True if there are specific methods represented in the map.
     */
    boolean hasIgnoreMethods() {
        return !ignoreMethods.isEmpty();
    }

    /**
     * @param methodName Add a method name to the {@link ValidationIgnore}
     */
    void addMethod(String methodName) {
        // we could see it either way
        ignoreMethods.add(methodName + "()");
        ignoreMethods.add(methodName);
    }

    /**
     * Checks to see if the specified method name should be ignored.
     *
     * @param methodName the name of the method to check against.
     * @return Will return true if the method is in the list of methods to ignore OR the entire class is set to be
     * ignored.
     */
    boolean shouldIgnoreMethod(String methodName) {
        return ignoreClassAll || ignoreMethods.contains(methodName);
    }
}
