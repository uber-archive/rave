package fixtures.floatrange;

import com.ubercab.rave.annotation.Validated;
import fixtures.SampleFactory;
import android.support.annotation.FloatRange;

@Validated(factory = SampleFactory.class)
public class BadFloatRangeUsage {
    @FloatRange(from = -5.5D, to = 10.5D)
    public int getInt1() {
        return 1;
    }
}
