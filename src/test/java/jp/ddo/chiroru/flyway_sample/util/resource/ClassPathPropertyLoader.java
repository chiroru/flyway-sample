package jp.ddo.chiroru.flyway_sample.util.resource;

import java.io.InputStream;

public class ClassPathPropertyLoader
        extends CachingPropertyLoader {

    private static final ClassPathPropertyLoader
    instance = new ClassPathPropertyLoader();

    public static ClassPathPropertyLoader getInstance() {
        return instance;
    }

    @Override
    protected InputStream getResourceAsStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }
}
