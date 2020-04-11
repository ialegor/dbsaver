package io.github.ialegor.util.resource;

import java.io.InputStream;

public final class ResourceUtils {

    public static InputStream getResourceStreamByName(Class<?> clazz, String name) throws IllegalArgumentException {
        InputStream stream = clazz.getResourceAsStream(name);
        if (stream == null) {
            stream = clazz.getClassLoader().getResourceAsStream(name);
            if (stream == null) {
                throw new IllegalArgumentException("Resource '" + name + "' not found");
            }
        }
        return stream;
    }
}
