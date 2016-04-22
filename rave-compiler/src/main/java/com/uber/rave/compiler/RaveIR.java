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

package com.uber.rave.compiler;

import android.support.annotation.NonNull;

import com.uber.rave.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the intermediate representation (IR) for a particular library. This class will hold everything required to
 * generate the model validation class for a library.
 */
final class RaveIR {

    /**
     * All the Elements with the {@link Validated} annotation.
     */
    @NonNull private final List<ClassIR> classIRs = new ArrayList<>();
    @NonNull private final String packageName;
    @NonNull private final String simpleName;

    /**
     * Create a new RAVE IR.
     * @param packageName the package name of the class that will be generted.
     * @param simpleName the simple name of the class to be generated.
     */
    RaveIR(@NonNull String packageName, @NonNull String simpleName) {
        this.packageName = packageName;
        this.simpleName = simpleName;
    }

    /**
     * Add a new {@link ClassIR} to the IR.
     * @param classIRs the {@link ClassIR} to add.
     */
    void addClassIR(@NonNull ClassIR classIRs) {
        this.classIRs.add(classIRs);
    }

    /**
     * @return Returns the list of {@link ClassIR}s that were added to this IR.
     */
    @NonNull
    List<ClassIR> getClassIRs() {
        return classIRs;
    }

    /**
     * @return Returns the number of {@link ClassIR} that.
     */
    int getNumClasses() {
        return classIRs.size();
    }

    /**
     * @return The string name representing the package of the to-be-generated class.
     */
    @NonNull
    String getPackageName() {
        return packageName;
    }

    /**
     * @return This simple name of the generated class.
     */
    @NonNull
    String getSimpleName() {
        return simpleName;
    }
}
