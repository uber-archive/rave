package fixtures.testSize;

import fixtures.SampleFactory;

import android.support.annotation.Size;

import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class BadSizeAnnotation {

    public BadSizeAnnotation () {};

    @Size(multiple = -1)
    public String checkDefaultSize1() {
        return "";
    }
}
