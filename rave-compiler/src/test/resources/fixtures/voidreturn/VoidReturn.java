package fixtures.voidreturn;

import fixtures.SampleFactory;
import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class VoidReturn {

    private String test;

    public void getIsTrue() {
        test = "Hey Now";
    }
}
