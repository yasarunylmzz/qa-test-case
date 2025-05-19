package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverManager {
    public static WebDriver driver;
    public static String browser;
    public static WebDriverWait wait;

    public static WebDriver getDriver() {
        if (driver == null) {
            browser = ConfigurationReader.getProperty("browser");
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options = new FirefoxOptions();
                    driver = new FirefoxDriver(options);

                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
            }
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(40));
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}