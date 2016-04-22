package fixtures.test5;

import fixtures.SampleFactory;

import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class ProtectedClassWrongPackage<T> {

    @Validated(factory = SampleFactory.class)
    protected static class ProtectedInnerClass {

    }
}
