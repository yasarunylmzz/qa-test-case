package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties properties;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/yasarunyilmaz/IdeaProjects/com.testcase/src/test/java/resource/config/properties");
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found!", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}