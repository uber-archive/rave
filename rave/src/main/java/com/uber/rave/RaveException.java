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

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * The exception thrown when validation fails.
 */
public abstract class RaveException extends Exception {

    @NonNull final List<RaveError> errors;

    /**
     * Creates a Rave Exception with the input errors.
     *
     * @param errors the errors to add.
     */
    RaveException(@NonNull List<RaveError> errors) {
        super(getFirstString(errors));
        this.errors = errors;
    }

    /**
     * @return Returns the list of error messages as a single string message.
     */
    @Override
    @NonNull
    public final String getMessage() {
        StringBuilder builder = new StringBuilder();
        ListIterator<RaveError> iterator = errors.listIterator(errors.size());
        String newline = "\n";
        while (iterator.hasPrevious()) {
            builder.append(iterator.previous().toString()).append(newline);
        }
        return builder.toString();
    }

    /**
     * This method will return an iterator over the {@link RaveError}s which can be used to view error details or
     * modify the error list.
     * @return An {@link Iterator} of {@link RaveError}s.
     */
    public final Iterator<RaveError> getRaveErrorIterator() {
        return errors.iterator();
    }

    @NonNull
    private static String getFirstString(@NonNull List<RaveError> errors) {
        return errors.isEmpty() ? "" : errors.get(0).toString();
    }
}
