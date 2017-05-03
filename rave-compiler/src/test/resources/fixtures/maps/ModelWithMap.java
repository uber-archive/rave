package fixtures.maps;

import com.uber.rave.annotation.Validated;
import java.util.Map;
import fixtures.SampleFactory;
import android.support.annotation.Nullable;

@Validated(factory = SampleFactory.class)
public class ModelWithMap {
    private Map<Object, Object> map;

    @Nullable
    public Map<Object, Object> getMap() {
        return map;
    }
}
