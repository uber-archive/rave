package fixtures.floatrange;

import com.uber.rave.annotation.Validated;
import fixtures.SampleFactory;
import android.support.annotation.FloatRange;

@Validated(factory = SampleFactory.class)
public class FloatRangeTestClass {
    @FloatRange(from = -5.5D, to = 10.5D)
    public double getDouble1() {
        return 1;
    }
    @FloatRange(from = -5D)
    public double getDouble2() {
        return 1;
    }
    @FloatRange(to = 10D)
    public double getDouble3() {
        return 1;
    }
    @FloatRange
    public double getDouble4() {
        return 1;
    }
    @FloatRange(from = -5.5D, to = 10.5D)
    public float getDouble5() {
        return 1;
    }
    @FloatRange(from = -5D)
    public float getDouble6() {
        return 1;
    }
    @FloatRange(to = 10D)
    public float getDouble7() {
        return 1;
    }
    @FloatRange
    public float getDouble8() {
        return 1;
    }
    @FloatRange(from = Double.MIN_VALUE, to = Double.MAX_VALUE)
    public float getDouble9() {
        return 1;
    }
}
