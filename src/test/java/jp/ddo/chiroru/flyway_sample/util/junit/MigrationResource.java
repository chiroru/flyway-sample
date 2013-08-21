package jp.ddo.chiroru.flyway_sample.util.junit;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import jp.ddo.chiroru.flyway_sample.util.resource.ClassPathPropertyLoader;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.flyway.core.Flyway;

public class MigrationResource
extends ExternalResource {

    private Logger L = LoggerFactory.getLogger(MigrationResource.class);

    private final static String FLYWAY_PROPERTY_FILE_PATH = "flyway.properties";

    private Properties props;
    private Flyway flyway;

    public MigrationResource() {
        try {
            ClassPathPropertyLoader loader = new ClassPathPropertyLoader();
            props = loader.load(FLYWAY_PROPERTY_FILE_PATH);
            flyway = new Flyway();
            flyway.setSchemas(props.getProperty("jdbc.schema"));
            flyway.setDataSource(getDataSource());
        } catch (IOException e) {
            throw new RuntimeException("設定ファイルのロードに失敗しました.");
        }
    }

    @Override
    protected void before() throws Throwable {
        L.info("[Migration Start] ==================================================");
        L.info(" Initialize the Database for Unit Testing in version " + flyway.info().current().getVersion());
        flyway.clean();
        flyway.migrate();
        L.info("[Migration END] ====================================================");
    }

    @Override
    protected void after() {
        // do nothing.
    }

    private DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(props.getProperty("jdbc.url"));
        dataSource.setDriverClassName(props.getProperty("jdbc.driver"));
        dataSource.setUsername(props.getProperty("jdbc.username"));
        dataSource.setPassword(props.getProperty("jdbc.password"));
        return dataSource;
    }
}
