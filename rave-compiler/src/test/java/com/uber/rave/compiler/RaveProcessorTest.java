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

import com.google.testing.compile.JavaFileObjects;
import com.uber.rave.annotation.MustBeFalse;
import com.uber.rave.annotation.MustBeTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

public class RaveProcessorTest {

    private RaveProcessor raveProcessor;
    private ArrayList<JavaFileObject> sources;
    private JavaFileObject myValidator;

    @Before
    public void setUp() {
        raveProcessor = new RaveProcessor();
        sources = new ArrayList<>();
        myValidator = JavaFileObjects.forResource("fixtures/SampleFactory.java");
    }

    @Test
    public void testStringDef_whenUsingStringDef_shouldMatchOutput() {
        JavaFileObject source = JavaFileObjects.forResource("fixtures/string_def/StringDefCases.java");
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/string_def/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(source);
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError()
                .and()
                .generatesSources(fileObject);
    }

    @Test
    public void testModelWithMap_whenModelHasAMapField_shouldGeneratedValidationForMap() {
        JavaFileObject source = JavaFileObjects.forResource("fixtures/maps/ModelWithMap.java");
        sources.add(source);
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource("fixtures/maps/SampleFactory_Generated_Validator.java"));
    }

    @Test
    public void testIntDef_whenUsingIntDef_shouldMatchOutput() {
        JavaFileObject source = JavaFileObjects.forResource("fixtures/intdef/IntDefTestClass.java");
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/intdef/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(source);
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError().and().generatesSources(fileObject);
    }

    @Test
    public void testFloatRange_whenUsingFloatRange_shouldMatchOutput() {
        JavaFileObject source = JavaFileObjects.forResource("fixtures/floatrange/FloatRangeTestClass.java");
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/floatrange/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(source);
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError().and().generatesSources(fileObject);
    }

    @Test
    public void testIntDef_whenUsingBadIntDef_shouldReturnAnError() {
        JavaFileObject source = JavaFileObjects.forResource("fixtures/intdef/BadIntDefUse.java");
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/intdef/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(source);
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile().withErrorContaining(AnnotationVerifier.INTDEF_BAD_RETURN_TYPE_ERROR);
    }

    @Test
    public void testIntRange_whenUsingIntRange_shouldHaveCorrectOutput() {
        JavaFileObject source = JavaFileObjects.forResource("fixtures/intrange/IntRangeTestClass.java");
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/intrange/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(source);
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError().and().generatesSources(fileObject);
    }

    @Test
    public void test1SimpleCase_whenUsingInputsFilesFromtest1Dir_shouldSucceed() {
        JavaFileObject source = JavaFileObjects.forResource("fixtures/test1/SimpleCase.java");
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/test1/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(source);
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError().and().generatesSources(fileObject);
    }

    @Test
    public void test2MultipleModels_whenUsingInputsFilesFromTest2Dir_shouldSucceed() {
        String fileContents = null;
        String filePath = "src/test/resources/fixtures/test2/MyFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("com.uber.rave.compiler"
                + ".MyFactory_Generated_Validator", fileContents);
        sources.add(JavaFileObjects.forResource("fixtures/test2/InheritFrom.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/ValidateByInterface.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/MultiMethodSampleModel.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/SingleMethodSampleModel.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/ArrayNotNull.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/AbstractAnnotated.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/NonAnnotated.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/IntDefModel.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/IntRangeTestModel.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test2/FloatRangeTestModel.java"));
        JavaFileObject myValidator = JavaFileObjects.forResource("fixtures/test2/MyFactory.java");
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError().and().generatesSources(fileObject);
    }

    @Test
    public void test3ComplexModelInheritance_whenUsingInputsFilesFromtest3Dir_shouldSucceed() {
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/test3/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(JavaFileObjects.forResource("fixtures/test3/InheritFrom.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test3/ValidateByInterface.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test3/ValidateBySecondInterface.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test3/ValidateSample.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test3/ValidateSample2.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError().and().generatesSources(fileObject);
    }

    @Test
    public void test4ComplexModelInheritanceWithInterface_whenUsingInputsFilesFromTest4Dir_shouldSucceed() {
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/test4/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(JavaFileObjects.forResource("fixtures/test4/InheritFrom.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test4/ValidateByInterface.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test4/ValidateSample2.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError().and().generatesSources(fileObject);
    }

    @Test
    public void testSizeIsSizeOk_whenDifferentValuesForSizeAnnotation_shouldSucceed() {
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/testSize/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(JavaFileObjects.forResource("fixtures/testSize/SimpleCase.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError().and().generatesSources(fileObject);
    }

    @Test
    public void testBadSizeAnnotation_whenInvalidValuesforSizeAnnotation_shouldFail() {
        String fileContents = null;
        String filePath = "build/resources/test/fixtures/testSize/SampleFactory_Generated_Validator.java";
        try {
            fileContents = readFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file: " + filePath);
        }
        JavaFileObject fileObject = JavaFileObjects.forSourceString("fixtures.SampleFactory_Generated_Validator",
                fileContents);
        sources.add(JavaFileObjects.forResource("fixtures/testSize/BadSizeAnnotation.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile().withErrorContaining("Multiple value is less than 1 with value");
    }

    @Test
    public void TrueFalseAnnotations_whenAnnotationsTrueFalseConflict_shouldFailCompile() {
        sources.add(JavaFileObjects.forResource("fixtures/test5/ConflictingMustbeTrue.java"));
        sources.add(myValidator);
        String expected = "Annotations " + MustBeFalse.class.getCanonicalName() + " cannot be used with "
                + MustBeTrue.class.getCanonicalName();
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile().withErrorContaining(expected);
    }

    @Test
    public void checkFactoryClass_whenTwoFactoriesAreReferenced_shouldFailAnnotationProcessing() {
        sources.add(JavaFileObjects.forResource("fixtures/test6/ModelUsesSampleFactory.java"));
        sources.add(JavaFileObjects.forResource("fixtures/test6/ModelUsesMyFactory.java"));
        sources.add(myValidator);
        JavaFileObject otherValidator = JavaFileObjects.forResource("fixtures/test2/MyFactory.java");
        sources.add(otherValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile();
    }

    @Test
    public void verifyValidatedAnnotations_whenModelIsPrivate_shouldFailCompile() {
        sources.add(JavaFileObjects.forResource("fixtures/test5/PrivateClass.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile().withErrorContaining("Class is private. It must be at least package private");
    }

    @Test
    public void verifyValidatedAnnotations_whenModelIsProtected_shouldFailCompile() {
        sources.add(JavaFileObjects.forResource("fixtures/test5/ProtectedClassWrongPackage.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile().withErrorContaining("is not visible to");
    }

    @Test
    public void verifyValidatedAnnotations_whenModelIsPackagePrivate_shouldFailCompile() {
        sources.add(JavaFileObjects.forResource("fixtures/test5/PackagePrivateClass.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile();
    }

    @Test
    public void verifyValidatedAnnotations_whenFloatRangeIsOnWrongReturnType_shouldFailCompile() {
        sources.add(JavaFileObjects.forResource("fixtures/floatrange/BadFloatRangeUsage.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile().withErrorContaining(AnnotationVerifier.FLOAT_RANGE_BAD_RETURN_TYPE_ERROR);
    }

    @Test
    public void verifyValidatedAnnotations_whenIntRangeIsOnWrongReturnType_shouldFailCompile() {
        sources.add(JavaFileObjects.forResource("fixtures/intrange/BadIntRangeUsage.java"));
        sources.add(myValidator);
        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .failsToCompile().withErrorContaining(AnnotationVerifier.INT_RANGE_BAD_RETURN_TYPE_ERROR);
    }

    @Test
    public void testStrictModeValidationStategy_whenNoAnnotationPresent_shouldTreatAsNonNull() {
        sources.add(JavaFileObjects.forResource("fixtures/validationstrategy/simple/SimpleCase.java"));
        sources.add(JavaFileObjects.forResource("fixtures/validationstrategy/StrictModeFactory.java"));

        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects
                        .forResource("fixtures/validationstrategy/simple/StrictModeFactory_Generated_Validator.java"));
    }

    @Test
    public void testStrictModeValidationStategy_whenPrimativePresent_shouldNotValidatePrimative() {
        sources.add(JavaFileObjects.forResource("fixtures/validationstrategy/primative/PrimativeCase.java"));
        sources.add(JavaFileObjects.forResource("fixtures/validationstrategy/StrictModeFactory.java"));

        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(
                        "fixtures/validationstrategy/primative/StrictModeFactory_Generated_Validator.java"));
    }

    @Test
    public void testExcludedMethod_shouldNotGenerateValidateMethodExcluded() {
        sources.add(JavaFileObjects.forResource("fixtures/excluded/UseOfExcluded.java"));
        sources.add(JavaFileObjects.forResource("fixtures/SampleFactory.java"));

        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(
                        "fixtures/excluded/SampleFactory_Generated_Validator.java"));
    }

    public void testDefaultModeValidationStategy_whenUnannotatedMethodAndPrimativePresent_shouldValidate() {
        sources.add(JavaFileObjects.forResource("fixtures/unannotated/UnannotatedField.java"));
        sources.add(myValidator);

        assertAbout(javaSources()).that(sources)
                .processedWith(raveProcessor)
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(
                        "fixtures/unannotated/SampleFactory_Generated_Validator.java"));
    }

    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }
}
