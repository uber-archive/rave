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
 * This class creates a collection of type {@code T}. This creator will require an additional creator for the
 * type in the collection.
 */
public class CollectionCreator<T> extends ObjectCreator<Collection<T>> {

    /**
     * @param spec        the {@link AnnotationSpecs} for this creator
     * @param itemCreator the {@link ObjectCreator} for the generic parameter of this class.
     */
    public CollectionCreator(AnnotationSpecs spec, ObjectCreator<T> itemCreator) {
        createCollections(spec, itemCreator);
    }

    /**
     * Creates the invalid and valid collection with the generic type for this creator.
     *
     * @param spec        the {@link AnnotationSpecs}.
     * @param itemCreator itemCreator the {@link ObjectCreator} for the generic parameter of this class.
     */
    private void createCollections(AnnotationSpecs spec, ObjectCreator<T> itemCreator) {
        if (spec.isNullable()) {
            addValidType(null);
        } else {
            addInvalidType(null);
        }
        if (spec.getSizeMax() >= 0) {
            int maxSize = Utils.roundDown(spec.getSizeMax(), spec.getSizeMultiple());
            if (maxSize >= spec.getSizeMin()) {
                addValidType(createValidCollectionOfSize(maxSize, itemCreator));
            }
            int minSize = Utils.roundUp(spec.getSizeMin(), spec.getSizeMultiple());
            if (minSize <= spec.getSizeMax()) {
                addValidType(createValidCollectionOfSize(minSize, itemCreator));
            }

            int middle = (int) ((spec.getSizeMax() + spec.getSizeMin()) / 2L);
            // Invalid because of content
            if (itemCreator.hasInvalidNext()) {
                addInvalidType(createInvalidCollectionOfSize(maxSize, itemCreator));
                addInvalidType(createInvalidCollectionOfSize(minSize, itemCreator));
                if (spec.getSizeMultiple() == 1) {
                    addInvalidType(createInvalidCollectionOfSize(middle, itemCreator));
                }
            }
            if (spec.getSizeMultiple() == 1) {
                addValidType(createValidCollectionOfSize(middle, itemCreator));
            }
            // Invalid because of size.
            addInvalidType(createValidCollectionOfSize(maxSize + 1, itemCreator));
            if (spec.getSizeMin() > 0) {
                addInvalidType(createValidCollectionOfSize(minSize - 1, itemCreator));
            }
        }
    }

    private Collection<T> createValidCollectionOfSize(long size, ObjectCreator<T> itemCreator) {
        Collection<T> collection = new ArrayList<>((int) size);
        for (int i = 0; i < size; i++) {
            collection.add(itemCreator.getValidItem());
            itemCreator.incrementValidNext();
        }
        return collection;
    }

    private Collection<T> createInvalidCollectionOfSize(long size, ObjectCreator<T> itemCreator) {
        Collection<T> collection = new ArrayList<>((int) size);
        for (int i = 0; i < size; i++) {
            collection.add(itemCreator.getInvalidItem());
            itemCreator.incrementInvalidNext();
        }
        return collection;
    }
}
