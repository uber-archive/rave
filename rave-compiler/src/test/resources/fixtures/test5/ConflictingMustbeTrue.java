package fixtures.test5;

import fixtures.SampleFactory;
import androidx.annotation.NonNull;

import com.uber.rave.annotation.MustBeFalse;
import com.uber.rave.annotation.MustBeTrue;
import com.uber.rave.annotation.Validated;

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
