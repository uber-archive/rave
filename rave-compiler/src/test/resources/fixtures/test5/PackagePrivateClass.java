package fixtures.test5;

import fixtures.SampleFactory;

import com.uber.rave.annotation.Validated;


public class PackagePrivateClass<T> {

    @Validated(factory = SampleFactory.class)
    static class PackagePrivateInnerClass {

    }
}
