package fixtures.intdef;

import com.uber.rave.annotation.Validated;
import fixtures.SampleFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import androidx.annotation.IntDef;

@Validated(factory = SampleFactory.class)
public class IntDefTestClass {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS, MAX, MIN})
    public @interface NavigationMode {}
    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_TABS = 2;
    public static final int MAX = Integer.MAX_VALUE;
    public static final int MIN = Integer.MIN_VALUE;

    @NavigationMode
    public int getStandard() {
        return NAVIGATION_MODE_STANDARD;
    }
}
