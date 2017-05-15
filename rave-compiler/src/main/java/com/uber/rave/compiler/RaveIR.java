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

import com.uber.rave.Validator;
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
    private final List<ClassIR> classIRs = new ArrayList<>();
    private final String packageName;
    private final String simpleName;
    private final Validator.Mode mode;

    /**
     * Create a new RAVE IR.
     * @param packageName the package name of the class that will be generted.
     * @param simpleName the simple name of the class to be generated.
     * @param mode the validation mode to be applied when generating validation code.
     */
    RaveIR(String packageName, String simpleName, Validator.Mode mode) {
        this.packageName = packageName;
        this.simpleName = simpleName;
        this.mode = mode;
    }

    /**
     * Add a new {@link ClassIR} to the IR.
     * @param classIRs the {@link ClassIR} to add.
     */
    void addClassIR(ClassIR classIRs) {
        this.classIRs.add(classIRs);
    }

    /**
     * @return Returns the list of {@link ClassIR}s that were added to this IR.
     */
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
    String getPackageName() {
        return packageName;
    }

    /**
     * @return This simple name of the generated class.
     */
    String getSimpleName() {
        return simpleName;
    }

    /**
     * @return The validation mode for this library.
     */
    Validator.Mode getMode() {
        return mode;
    }
}
