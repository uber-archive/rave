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

/**
 * This class increments all the {@link ObjectCreator}s in an appropriate fashion for both valid and invalid
 * states.
 */
public class ObjectCreatorIncrementer {
    private final ObjectCreator<?>[] creators;
    private int creatorIndex = 0;

    /**
     * @param creators the creators this incrementor should handle.
     */
    public ObjectCreatorIncrementer(ObjectCreator<?>... creators) {
        this.creators = creators;
        for (ObjectCreator<?> creator : creators) {
            creator.setReturnOnlyValidItems(true);
        }
        this.creators[creatorIndex].setReturnOnlyValidItems(false);
    }

    /**
     * Checks to see if any {@link ObjectCreator} has and additional valid permutations that can be outputed.
     *
     * @return true if more valid items are available from any creator, false otherwise.
     */
    public boolean hasValidPermutations() {
        for (ObjectCreator<?> creator : creators) {
            if (creator.hasValidNext()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Increment all the valid creators to the next valid item.
     */
    public void incrementValidCreators() {
        for (ObjectCreator<?> creator : creators) {
            creator.incrementValidNext();
        }
    }

    /**
     * Checks to see if any {@link ObjectCreator} has and additional invalid permutations that can be outputed.
     *
     * @return true if more invalid items are available from any creator, false otherwise.
     */
    public boolean hasInvalidPermutations() {
        for (ObjectCreator<?> creator : creators) {
            if (creator.hasInvalidNext()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Increment one of the invalid creators to the next valid item. This is different from the valid incrementor method
     * because for invalid items you want to get only one invalid object at a time to ensure that you test each invalid
     * item independently. This method also sets all other creators in the list to return valid items even if the
     * {@link ObjectCreator#getInvalidItem()} is called.
     */
    public void incrementInvalidCreators() {
        if (creatorIndex >= creators.length) {
            return;
        }
        creators[creatorIndex].setReturnOnlyValidItems(false);
        if (creators[creatorIndex].hasInvalidNext()) {
            creators[creatorIndex].incrementInvalidNext();
        } else {
            advanceToNextCreator();
        }
    }

    private void advanceToNextCreator() {
        creators[creatorIndex].setReturnOnlyValidItems(true);
        creatorIndex++;
        if (creators[creatorIndex].hasInvalidNext()) {
            creators[creatorIndex].setReturnOnlyValidItems(false);
        } else {
            advanceToNextCreator();
        }
    }
}
