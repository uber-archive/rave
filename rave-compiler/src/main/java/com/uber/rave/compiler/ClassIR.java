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

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * This class represents the intermediate representation (IR) of all the information required to generate the method
 * for a particular data model class.
 */
final class ClassIR {

    private final List<MethodIR> methodIRs;
    private final List<TypeMirror> inheritedTypes;
    private final TypeMirror typeMirror;

    /**
     * Create a {@link ClassIR} object.
     * @param typeMirror the type mirror of the class this ir represents.
     */
    ClassIR(TypeMirror typeMirror) {
        methodIRs = new ArrayList<>();
        inheritedTypes = new ArrayList<>();
        this.typeMirror = typeMirror;
    }

    /**
     * Adds a {@link MethodIR} to this {@link ClassIR}.
     * @param methodIRs the {@link MethodIR} to add.
     */
    void addMethodIR(MethodIR methodIRs) {
        this.methodIRs.add(methodIRs);
    }

    /**
     * @return Returns a list of all the methods added to this method.
     */
    List<MethodIR> getAllMethods() {
        return methodIRs;
    }

    /**
     * @return The {@link TypeMirror} of the class this IR represents.
     */
    TypeMirror getTypeMirror() {
        return typeMirror;
    }

    /**
     * Add the types that are inherited by this class.
     * @param typeMirror The type mirror to add.
     * @param typesUtils The {@link Types} utility needed to add the type mirror here.
     */
    void addInheritedTypes(TypeMirror typeMirror, Types typesUtils) {
        if (!CompilerUtils.typeMirrorInCollection(inheritedTypes, typeMirror, typesUtils)) {
            inheritedTypes.add(typeMirror);
        }
    }

    /**
     * @return returns the list of inherited type mirror classes.
     */
    List<TypeMirror> getInheritedTypes() {
        return inheritedTypes;
    }
}
