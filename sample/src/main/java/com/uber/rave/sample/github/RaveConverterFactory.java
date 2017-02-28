package com.uber.rave.sample.github;

import com.uber.rave.Rave;
import com.uber.rave.RaveException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * A converter factory that uses Rave to validate responses from the network. In the event that a response doesn't
 * validate an exception is thrown to consumers.
 *
 * NOTE: This is a forwarding converter that delegates to another converter that should convert Java Objects
 * into JSON and back. This converter must registered to your Retrofit instance before any other converter it might
 * delegate to.
 */
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

    /**
     * A converter that validates responses received from the network using rave.
     */
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
