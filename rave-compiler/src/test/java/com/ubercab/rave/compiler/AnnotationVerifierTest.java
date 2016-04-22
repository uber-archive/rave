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

package com.ubercab.rave.compiler;

import org.junit.Test;
import org.mockito.Matchers;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnnotationVerifierTest {
    @Test(expected = AbortProcessingException.class)
    public void verify_whenTypeElementDoesNotHaveAnnotation_shouldAbortWithError() {
        Messager messager = mock(Messager.class);
        Elements element = mock(Elements.class);
        Types types = mock(Types.class);
        AnnotationVerifier verifier = new AnnotationVerifier(messager, element, types);
        TypeElement typeElement = mock(TypeElement.class);
        verifier.verify(typeElement);
        verify(messager).printMessage(eq(Diagnostic.Kind.ERROR), Matchers.anyString(), eq(typeElement));
    }

    @Test(expected = AbortProcessingException.class)
    public void verify_whenTypeElementIsPasckagePrivate_shouldAbortWithError() {
        Set<Modifier> modSet = new HashSet<>();
        modSet.add(Modifier.PROTECTED);
        Messager messager = mock(Messager.class);
        Elements element = mock(Elements.class);
        Types types = mock(Types.class);
        AnnotationVerifier verifier = new AnnotationVerifier(messager, element, types);
        PackageElement packageElement = mock(PackageElement.class);
        Name name = mock(Name.class);
        when(packageElement.getQualifiedName()).thenReturn(name);
        when(name.toString()).thenReturn("Nonmatching.package");
        TypeElement typeElement = mock(TypeElement.class);
        when(typeElement.getModifiers()).thenReturn(modSet);
        when(typeElement.getEnclosingElement()).thenReturn(packageElement);
        verifier.verify(typeElement);
        verify(messager).printMessage(eq(Diagnostic.Kind.ERROR), Matchers.anyString(), eq(typeElement));
    }
}
