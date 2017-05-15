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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

/**
 * This is the base class for object creators.
 */
public abstract class ObjectCreator<T> implements ParameterizedBuilder<T> {
    private final List<T> validTypes = new ArrayList<>();
    private final List<T> invalidTypes = new ArrayList<>();
    private int validIndex = 0;
    private int invalidIndex = 0;
    /**
     * This is used by the {@link ObjectCreatorIncrementer} for return permutations of invalid objects.
     */
    private boolean returnOnlyValid = false;

    /**
     * Returns the current valid item that {@link ObjectCreator#validIndex} is pointing to.
     *
     * @return the current valid item.
     */
    @Nullable
    public final T getValidItem() {
        if (validTypes.isEmpty()) {
            return null;
        }
        return validTypes.get(validIndex % validTypes.size());
    }

    /**
     * @return get the next invalid item. Note if this creator is in valid only mode than this method will return
     * a valid item instead. See {@link ObjectCreator#setReturnOnlyValidItems(boolean)} method.
     */
    @Nullable
    public final T getInvalidItem() {
        if (returnOnlyValid) {
            return getValidItem();
        }
        if (invalidTypes.isEmpty()) {
            return null;
        }
        return invalidTypes.get(invalidIndex % invalidTypes.size());
    }

    @Override
    public final Collection<T> getValidCases() {
        return validTypes;
    }

    @Override
    public final Collection<T> getInvalidCases() {
        return invalidTypes;
    }

    /**
     * Checks to see if this creator has any additional valid items left.
     *
     * @return true if any valid items are available, false otherwise.
     */
    final boolean hasValidNext() {
        return validIndex < validTypes.size();
    }

    /**
     * Increment the valid index into the valid items array to the next one. This index can be increment indefinitely
     * since it will wrap around if it is too big.
     */
    final void incrementValidNext() {
        validIndex++;
    }

    /**
     * Checks to see if this creator has any additional invalid items left.
     *
     * @return true if any invalid items are available, false otherwise.
     */
    final boolean hasInvalidNext() {
        return invalidIndex < invalidTypes.size();
    }

    /**
     * Increment the invalid index into the invalid items array to the next one. This index can be increment
     * indefinitely since it will wrap around if it is too big.
     */
    final void incrementInvalidNext() {
        invalidIndex++;
    }

    /**
     * This method allows the user to toggle a valid only mode for this creator. This method is primarily used by the
     * {@link ObjectCreatorIncrementer} class.
     *
     * @param returnOnlyValid if true this creator will be set into a valid only mode which means even when the
     *                        {@link ObjectCreator#getInvalidItem()} is used it will still return a valid item.
     */
    final void setReturnOnlyValidItems(boolean returnOnlyValid) {
        this.returnOnlyValid = returnOnlyValid;
    }

    /**
     * Add any additional valid items.
     *
     * @param t the item to add.
     */
    protected final void addInvalidType(T t) {
        invalidTypes.add(t);
    }

    /**
     * Add any additional invalid items
     *
     * @param t the invalid item to add.
     */
    protected final void addValidType(T t) {
        validTypes.add(t);
    }
}
