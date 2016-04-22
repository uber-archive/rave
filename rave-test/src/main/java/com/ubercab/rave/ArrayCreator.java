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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Create an array of Objects of type {@code T}. Each item in the array created will either be valid or invalid (with
 * respect to validation) if the array is meant to be valid or invalid, respectively.
 * @param <T> the type of the object.
 */
public final class ArrayCreator<T> extends ObjectCreator<T[]> {
    private final Class<T> clazz;

    /**
     * @param spec        the {@link AnnotationSpecs} for this creator.
     * @param itemCreator the {@link ObjectCreator} for the generic parameter of this class.
     * @param clazz       the {@link Class} of the items in the array.
     */
    public ArrayCreator(AnnotationSpecs spec, ObjectCreator<T> itemCreator, Class<T> clazz) {
        this.clazz = clazz;
        createArrays(spec, itemCreator);
    }

    /**
     * Creates the invalid and valid arrays with the generic type for this creator.
     *
     * @param spec        the {@link AnnotationSpecs}.
     * @param itemCreator itemCreator the {@link ObjectCreator} for the generic parameter of this class.
     */
    private void createArrays(AnnotationSpecs spec, ObjectCreator<T> itemCreator) {
        if (spec.isNullable()) {
            addValidType(null);
        } else {
            addInvalidType(null);
        }
        if (spec.getSizeMax() >= 0) {
            int maxSize = Utils.roundDown(spec.getSizeMax(), spec.getSizeMultiple());
            if (maxSize >= spec.getSizeMin()) {
                addValidType(createValidArrayOfSize(maxSize, itemCreator));
            }
            int minSize = Utils.roundUp(spec.getSizeMin(), spec.getSizeMultiple());
            if (minSize <= spec.getSizeMax()) {
                addValidType(createValidArrayOfSize(minSize, itemCreator));
            }
            int middle = (int) ((spec.getSizeMax() + spec.getSizeMin()) / 2L);
            // Invalid because of content
            if (itemCreator.hasInvalidNext()) {
                addInvalidType(createInvalidArrayOfSize(maxSize, itemCreator));
                addInvalidType(createInvalidArrayOfSize(minSize, itemCreator));
                if (spec.getSizeMultiple() == 1) {
                    addInvalidType(createInvalidArrayOfSize(middle, itemCreator));
                }
            }
            if (spec.getSizeMultiple() == 1) {
                addValidType(createValidArrayOfSize(middle, itemCreator));
            }
            // Invalid because of size.
            addInvalidType(createValidArrayOfSize(maxSize + 1, itemCreator));
            if (minSize > 0) {
                addInvalidType(createValidArrayOfSize(minSize - 1, itemCreator));
            }
        }
    }

    private T[] createValidArrayOfSize(int size, ObjectCreator<T> itemCreator) {
        Collection<T> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            collection.add(itemCreator.getValidItem());
            itemCreator.incrementValidNext();
        }
        return toArray(collection, clazz);
    }

    private T[] createInvalidArrayOfSize(int size, ObjectCreator<T> itemCreator) {
        Collection<T> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            collection.add(itemCreator.getInvalidItem());
            itemCreator.incrementInvalidNext();
        }
        return toArray(collection, clazz);
    }

    public T[] toArray(Collection<T> list, Class<T> k) {
        return list.toArray(
                (T[]) java.lang.reflect.Array.newInstance(k, list.size()));
    }
}
