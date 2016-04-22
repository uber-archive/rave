package fixtures.test5;

import fixtures.SampleFactory;
import android.support.annotation.NonNull;

import com.ubercab.rave.annotation.MustBeFalse;
import com.ubercab.rave.annotation.MustBeTrue;
import com.ubercab.rave.annotation.Validated;

@Validated(factory = SampleFactory.class)
public class ConflictingMustbeTrue<T> {
    @NonNull
    public Object getIsFalse() {
        return "This is not a boolean";
    }
    @MustBeTrue
    @MustBeFalse
    public boolean getIsFailedAnnotationsMethod() {
        return false;
    }
}
