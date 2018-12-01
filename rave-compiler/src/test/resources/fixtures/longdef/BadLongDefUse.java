package fixtures.longdef;

import com.uber.rave.annotation.Validated;

import fixtures.SampleFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import android.support.annotation.LongDef;

@Validated(factory = SampleFactory.class)
public class BadLongDefUse {
    @Retention(RetentionPolicy.SOURCE)
    @LongDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS})
    public @interface NavigationMode {}
    public static final long NAVIGATION_MODE_STANDARD = 0L;
    public static final long NAVIGATION_MODE_LIST = 1L;
    public static final long NAVIGATION_MODE_TABS = 2L;

    // This method doesn't return a long.
    @NavigationMode
    public float getStandard() {
        return NAVIGATION_MODE_STANDARD;
    }
}
