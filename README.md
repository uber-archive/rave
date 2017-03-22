## RAVE (Runtime Annotation Validation Engine) [![Build Status](https://travis-ci.org/uber-common/rave.svg?branch=master)](https://travis-ci.org/uber-common/rave)

RAVE is a data model validation framework that uses Java annotation processing in order to generate runtime code to validate specific data constraints within your data models.


## Motivation

Using Java annotation processing, RAVE provides an efficient mechanism to allow engineers to place restrictions and checks on the data produced and consumed within a client code base. This allows the processor to take additional steps such as generate compiler errors, warnings and even generate source code.

RAVE will leverage the user-provided annotation to provide concise and efficient code to validate model data used in your Java code base. Regardless of the transport format, RAVE gives us flexible means to ensure that we are getting the data we expect and continue to expect throughout the data model lifecycle.

### Application of RAVE
* Validating schema's from the server and reporting when the server violates the contract.
* Validating schema from disk cache and avoiding errors caused by stale schemas.
* Validating models throughout their clientside lifecycle.
* Validating models after mutation.

## Installation
#### Gradle
To integrate RAVE into your project add the following to your dependencies in your 'build.gradle' file:

```
apt 'com.uber:rave-compiler:0.6.0'
```

```
compile 'com.uber:rave:0.6.0'
```

## Code Example

### Step 1: Create a Factory Class in your module/library.
There can only be one ValidatorFactory in any given module/library.
Example:

```java

public final class SampleFactory implements ValidatorFactory {
    @NonNull
    @Override
    public BaseValidator generateValidator() {
        return new SampleFactory_Generated_Validator();
    }
}

````

In the example above ```SampleFactory_Generated_Validator``` is generated once a model references the SampleFactory class using the ```@Validated``` annotation. See Step 2.

### Step 2: Annotate your models

Example:

```java

@Validated(factory = SampleFactory.class)
public class SimpleModel {

    private String notNullField;
    private String canBeNullField;
    private List<String> names;

    private static final String MATCHED = "Matched";
    private static final String MATCHING = "Matching";
    private static final String NOT_MATCHED = "NotMatched";

    @StringDef({MATCHED, MATCHING, NOT_MATCHED})
    @Retention(RetentionPolicy.SOURCE)
    @interface StringVals { }

    public SimpleModel() {}

    @NonNull
    @StringVals
    public String getNotNullField() {
        return notNullField;
    }

    @Size(5)
    public List<String> getNames() {
        return names;
    }

    @MustBeFalse
    public boolean getIsFalse() {
        return names.size() > 2 && canBeNullField != null;
    }

    @StringVals
    public String getSomeString() {
        return "Matched";
    }
}

```

### Step 3: Start Validating

```java


public void validateMyModel(SimpleModel myModel) {
    try {
        Rave.getInstance().validate(object);
    } catch (UnsupportedObjectException e) {
        // handle unsupported error case.
    } catch (RaveException e) {
        // handle rave validation error.
    }
}


```

### An example with Retrofit:
Here is a simple example extending a ```retrofit.converter.GsonConverter``` to use Rave.

```java

public class ValidatedGsonConverter extends GsonConverter {

    private final Rave mRave;

    public ValidatedGsonConverter(Gson gson, Rave rave) {
        super(gson);
        mRave = rave;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        Object object = super.fromBody(body, type);
        try {
            mRave.validate(object);
        } catch (UnsupportedObjectException e) {
            // handle unsupported error case.
        } catch (RaveException e) {
            // handle rave validation error.
        }
        return object;
    }

    ...
}
```


## API Reference
The two main RAVE APIs are as follows:
```java
    /**
     * Get an instance of RAVE validator.
     *
     * @return the singleton instance of the RAVE validator.
     */
    @NonNull
    public static synchronized Rave getInstance();

    /**
     * Validate an object. If the object is not supported, nothing will happen. Otherwise the object will be routed to
     * the correct sub-validator which knows how to validate it.
     *
     * @param object the object to be validated.
     * @throws RaveException if validation fails.
     */
    public synchronized void validate(@NonNull Object object) throws RaveException;
```

### Advanced Usage
There may be some cases in which you want to exclude/ignore certain models from validation. To do this we have the `ExclusionStrategy` class. Use the builder to exclude class or class + methods.

```java
 ExclusionStrategy builder = new ExclusionStrategy.Builder()
        .addMethod(MyExcludedClass.class, "excludedMethodName")
        .addMethod("path.to.class.MyExcludedClass", "otherMethodToExclude");
 Rave.getInstance().validate(object, builder.build());

````

## Supported Annotations
Supported annotations are listed [here](https://github.com/uber-common/rave/blob/master/rave-compiler/src/main/java/com/uber/rave/compiler/CompilerUtils.java#L54)

## Limitations

* Rave currently does not validate fields in a model. Rave only validates model methods. If you want a field in a model validated there must be a getter method for that field.

## Contributors

We'd love for you to contribute to our open source projects. Before we can accept your contributions, we kindly ask you to sign our [Uber Contributor License Agreement](https://docs.google.com/a/uber.com/forms/d/1pAwS_-dA1KhPlfxzYLBqK6rsSWwRwH95OCCZrcsY5rk/viewform).

- If you **find a bug**, open an issue or submit a fix via a pull request.
- If you **have a feature request**, open an issue or submit an implementation via a pull request
- If you **want to contribute**, submit a pull request.


## License
RAVE is released under a MIT license. See the LICENSE file for more information.
