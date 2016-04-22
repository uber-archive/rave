package fixtures.intrange;

import com.uber.rave.annotation.Validated;
import fixtures.SampleFactory;
import android.support.annotation.IntRange;

@Validated(factory = SampleFactory.class)
public class IntRangeTestClass {
    @IntRange(from = -5, to = 10)
    public int getInt1() {
        return 1;
    }
    @IntRange(from = -5)
    public int getInt2() {
        return 1;
    }
    @IntRange(to = 10)
    public int getInt3() {
        return 1;
    }
    @IntRange
    public int getInt4() {
        return 1;
    }
}
