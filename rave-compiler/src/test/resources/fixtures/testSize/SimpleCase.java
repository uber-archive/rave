package fixtures.testSize;

import fixtures.SampleFactory;

import android.support.annotation.Size;

import com.uber.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class SimpleCase {

    public SimpleCase () {};

    @Size(10)
    public String checkDefaultSize1() {
        return "";
    }

    @Size(min = 1, max = 15)
    public String checkDefaultSize2() {
        return "";
    }

    @Size(multiple = 2)
    public String checkDefaultSize3() {
        return "";
    }

    @Size(value = 10, min = 1, max = 3)
    public String checkDefaultSize4() {
        return "";
    }

    @Size(min = 1, max = 20, multiple = 5)
    public String checkDefaultSize5() {
        return "";
    }
}
