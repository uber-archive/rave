package fixtures.test5;

import fixtures.SampleFactory;

import com.uber.rave.annotation.Validated;


public class PrivateClass<T> {

    @Validated(factory = SampleFactory.class)
    private static class PrivateInnerClass {

    }
}
