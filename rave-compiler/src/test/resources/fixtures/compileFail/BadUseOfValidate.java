package fixtures.compileFail;

import com.uber.rave.annotation.Validated;

import fixtures.SampleFactory;

@Validated(factory = SampleFactory.class)
class BadUseOfValidate {
}
