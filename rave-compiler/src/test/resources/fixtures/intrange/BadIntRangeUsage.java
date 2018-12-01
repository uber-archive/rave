package fixtures.intrange;

import com.uber.rave.annotation.Validated;
import fixtures.SampleFactory;
import android.support.annotation.IntRange;

@Validated(factory = SampleFactory.class)
public class BadIntRangeUsage {
    @IntRange(from = -5L, to = 10L)
    public float getInt1() {
        return 1;
    }
}
