import com.ubercab.rave.annotation.Validated;
import com.ubercab.rave.compiler.MyFactory;

@Validated(factory = MyFactory.class)
public class ModelUsesMyFactory { }