package fixtures.longdef;

import com.uber.rave.annotation.Validated;
import fixtures.SampleFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import android.support.annotation.LongDef;

@Validated(factory = SampleFactory.class)
public class LongDefTestClass {
    @Retention(RetentionPolicy.SOURCE)
    @LongDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS, MAX, MIN})
    public @interface NavigationMode {}
    public static final long NAVIGATION_MODE_STANDARD = 0L;
    public static final long NAVIGATION_MODE_LIST = 1L;
    public static final long NAVIGATION_MODE_TABS = 2L;
    public static final long MAX = Long.MAX_VALUE;
    public static final long MIN = Long.MIN_VALUE;

    @NavigationMode
    public long getStandard() {
        return NAVIGATION_MODE_STANDARD;
    }
}
