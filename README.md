## RAVE (Runtime Annotation Validation Engine) [![Build Status](https://travis-ci.org/uber-common/rave.svg?branch=master)](https://travis-ci.org/uber-common/rave)

RAVE is a shield that prevents invalid data from crashing or causing hard to spot bugs in your Android apps. RAVE uses java annotation processing to leverage the annotations ([Nullness](https://developer.android.com/studio/write/annotations.html#adding-nullness), [Value Constraint](https://developer.android.com/studio/write/annotations.html#value-constraint), [Typedef](https://developer.android.com/studio/write/annotations.html#enum-annotations)) already present in your model classes to increase safety at runtime. Specifically, it ensures models adhere to the set of expectations that are described by their annotations.


## Motivation

Android apps consume data from a variety of sources (network, disk, etc.) that you as an app developer don’t have control over. When external APIs behave badly and return `null` for something that’s supposed to be `@NonNull`, your app can crash. Even when APIs behave well, sometimes their corner cases aren’t well-documented, are unknown or may change overtime. RAVE ensures the data you receive from these sources adheres to the set of expectations described by the annotations present on your models.

### Application of RAVE
* Validating responses from the network match what the client expects
* Avoiding errors caused by stale schemas when fetching data from disk
* Verifying models are valid after mutation
* Ensuring third party APIs don’t crash your app when providing unexpected data

## Installation
#### Gradle
To integrate RAVE into your project add the following to your 'build.gradle' file:

```
buildscript {
    repositories {
        maven { url 'https://maven.google.com' }
    }
}

dependencies {
  annotationProcessor 'com.uber:rave-compiler:2.1.0'
  implementation 'com.uber:rave:2.1.0'
}
```
#### Proguard Settings
To ensure validators are not removed by Proguard, add the following to your proguard configuration:
```
-keep class * implements com.uber.rave.ValidatorFactory
```

If you're using a version of the Android gradle plugin below `2.2` you need to use the [apt](https://bitbucket.org/hvisser/android-apt) plugin.

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

```

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
        Rave.getInstance().validate(myModel);
    } catch (UnsupportedObjectException e) {
        // handle unsupported error case.
    } catch (RaveException e) {
        // handle rave validation error.
    }
}

```

### Use with Retrofit 2:
Here's a simple recipe to use Rave with [Retrofit 2](https://github.com/square/retrofit). This is documented and included in the RAVE sample app.

```java
final class RaveConverterFactory extends Converter.Factory {

    static RaveConverterFactory create() {
        return new RaveConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {
        Converter<ResponseBody, ?> delegateConverter = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new RaveResponseConverter(delegateConverter);
    }

    private static final class RaveResponseConverter implements Converter<ResponseBody, Object> {

        private final Converter<ResponseBody, ?> delegateConverter;

        RaveResponseConverter(Converter<ResponseBody, ?> delegateConverter) {
            this.delegateConverter = delegateConverter;
        }

        @Override
        public Object convert(ResponseBody value) throws IOException {
            Object convert = delegateConverter.convert(value);
            try {
                Rave.getInstance().validate(convert);
            } catch (RaveException e) {
                // This response didn't pass RAVE validation, throw an exception.
                throw new RuntimeException(e);
            }
            return convert;
        }
    }
}

```

### Advanced Usage
There may be some cases in which you want to exclude/ignore certain models from validation. To do this apply `@Excluded` to  the method that should be exempted from validation. RAVE will not generate validation code for this method.

## Supported Annotations

A list of supported annotations can be found [here](https://github.com/uber-common/rave/blob/master/rave-compiler/src/main/java/com/uber/rave/compiler/CompilerUtils.java#L54).

## Limitations

Rave currently does not validate fields in a model. Rave only validates model methods. If you want a field in a model validated there must be a getter method for that field.

## Contributors

We'd love for you to contribute to our open source projects. Before we can accept your contributions, we kindly ask you to sign our [Uber Contributor License Agreement](https://docs.google.com/a/uber.com/forms/d/1pAwS_-dA1KhPlfxzYLBqK6rsSWwRwH95OCCZrcsY5rk/viewform).

- If you **find a bug**, open an issue or submit a fix via a pull request.
- If you **have a feature request**, open an issue or submit an implementation via a pull request.
- If you **want to contribute**, submit a pull request.


## License
RAVE is released under the Apache License, Version 2.0. See the LICENSE file for more information.
