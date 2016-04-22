package fixtures.test4;

import fixtures.SampleFactory;

import com.uber.rave.annotation.Validated;

/**
 * Sample class that shows how inheritence would work with validation.
 */
@Validated(factory = SampleFactory.class)
public class InheritFrom extends ValidateSample2 {

    protected InheritFrom(String notNullField) {
        super(notNullField);
    }
}
