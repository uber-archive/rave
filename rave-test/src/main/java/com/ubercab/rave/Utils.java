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
 * A simple utility class for some common methods.
 */
final class Utils {

    /**
     * Round n up to nearest multiple of m.
     *
     * @param n the number to round
     * @param m the number to round to.
     * @return the result of the calculation.
     */
    static int roundUp(long n, long m) {
        return (int) (((n + m - 1) / m) * m);
    }

    /**
     * Round n down to nearest multiple of m.
     *
     * @param n the number to round
     * @param m the number to round to.
     * @return the result of the calculation
     */
    static int roundDown(long n, long m) {
        return (int) ((n / m) * m);
    }
}
