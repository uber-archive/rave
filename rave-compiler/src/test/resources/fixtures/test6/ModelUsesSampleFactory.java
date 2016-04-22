import com.uber.rave.annotation.Validated;

import fixtures.SampleFactory;

@Validated(factory = SampleFactory.class)
public class ModelUsesSampleFactory { }