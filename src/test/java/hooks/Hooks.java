package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import utilities.ConfigurationReader;
import utilities.DriverManager;
import utilities.ScreenShotUtil;

public class Hooks {
    ScreenShotUtil screenShotUtil = new ScreenShotUtil();

    @Before
    @Given("I am on the Enuygun homepage")
    public void setUp() {
        String browser = ConfigurationReader.getProperty("browser");
        System.setProperty("browser", browser);
        String url = ConfigurationReader.getProperty("url");


        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(url);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            screenShotUtil.takeScreenshot(scenario.getName());
        }
        DriverManager.quitDriver();
    }

}
