package fixtures.floatrange;

import com.uber.rave.annotation.Validated;
import fixtures.SampleFactory;
import androidx.annotation.FloatRange;

@Validated(factory = SampleFactory.class)
public class BadFloatRangeUsage {
    @FloatRange(from = -5.5D, to = 10.5D)
    public int getInt1() {
        return 1;
    }
}
