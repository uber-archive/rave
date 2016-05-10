package fixtures.intdef;

import com.uber.rave.annotation.Validated;

import fixtures.SampleFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import android.support.annotation.IntDef;

@Validated(factory = SampleFactory.class)
public class BadIntDefUse {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS})
    public @interface NavigationMode {}
    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_TABS = 2;

    // This method doesn't return an int or long.
    @NavigationMode
    public float getStandard() {
        return NAVIGATION_MODE_STANDARD;
    }
}
