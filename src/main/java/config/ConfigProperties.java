package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    public static String getValueProperties(String key) throws IOException {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/resources/application.properties");
        property.load(fis);
        return property.getProperty(key);
    }
}
