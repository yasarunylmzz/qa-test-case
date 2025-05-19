package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import utilities.DriverManager;
import utilities.ScreenShotUtil;

public class Hooks {
    ScreenShotUtil screenShotUtil = new ScreenShotUtil();
    WebDriver driver = DriverManager.getDriver();

    @Before
    public void setUp() {
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            screenShotUtil.takeScreenshot(scenario.getName());
        }
        DriverManager.quitDriver();
    }

}
