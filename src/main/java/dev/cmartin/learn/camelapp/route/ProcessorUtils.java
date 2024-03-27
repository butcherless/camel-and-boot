package dev.cmartin.learn.camelapp.route;

import org.apache.camel.Exchange;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessorUtils {

    public static <T> T getBody(Exchange exchange, Class<T> clazz) {
        return exchange.getMessage().getBody(clazz);
    }

    public static <T> void setBody(Exchange exchange, String payload) {
        exchange.getMessage().setBody(payload);
    }

    public static <T extends RuntimeException> void handleErrors(final List<String> errors, final Class<T> clazz)
            throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {

        if (!errors.isEmpty()) {
            var errorList = errors.stream().collect(Collectors.joining(",", "[", "]"));
            throw clazz.getConstructor(String.class).newInstance(errorList);
        }
    }

}
