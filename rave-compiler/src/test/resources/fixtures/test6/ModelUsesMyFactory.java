import com.uber.rave.annotation.Validated;
import com.uber.rave.compiler.MyFactory;

@Validated(factory = MyFactory.class)
public class ModelUsesMyFactory { }