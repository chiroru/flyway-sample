package jp.ddo.chiroru.flyway_sample.util.resource;

import java.io.IOException;
import java.util.Properties;

public interface PropertyLoader {

    Properties load(String path) throws IOException;
}
