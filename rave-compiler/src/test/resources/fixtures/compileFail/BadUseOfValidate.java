package fixtures.compileFail;

import com.ubercab.rave.annotation.Validated;

import fixtures.SampleFactory;

@Validated(factory = SampleFactory.class)
class BadUseOfValidate {
}
