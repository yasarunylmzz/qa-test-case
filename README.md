# Enuygun QA Automation Test Project

This project provides end-to-end automation tests for the https://www.enuygun.com/ web application using Cucumber, JUnit, and Selenium WebDriver. Tests are written following the BDD (Behavior Driven Development) approach.

---

## Project Structure

```
com.testcase/
├── src/
│   ├── main/
│   └── test/
│       ├── java/
│       │   ├── analysis/           # Analysis classes (e.g., FlightAnalyzer)
│       │   ├── hooks/              # Test setup/teardown (Hooks)
│       │   ├── pages/              # Page Object Model classes
│       │   ├── pages/locators/     # Page locators
│       │   ├── runner/             # Test runner (TestRunner.java)
│       │   ├── steps/              # Step definitions (Case1Steps, Case3Steps, Case4Steps)
│       │   └── utilities/          # Utility classes (ConfigurationReader, DriverManager, etc.)
│       └── resources/
│           ├── features/           # .feature files (Case4_DataExtraction.feature, etc.)
│           └── config/             # Configuration file (properties)
└── README.md
```

---

## Prerequisites

- Java 11 or higher
- Maven installed

---

## Setup

1. **Clone the Repository**

   ```sh
   git clone <repo-url>
   cd com.testcase
   ```

2. **Install Dependencies**
   ```sh
   mvn clean install
   ```

---

## Configuration

- Edit the configuration file at `src/test/java/resources/config/properties` to set browser and URL:
  ```
  browser=chrome
  url=https://www.enuygun.com/
  ```

---

## Running the Tests

### 1. **From IDE**

- Right-click on `TestRunner.java`
- Edit @CucumberOptions --> edit this "features = "src/test/java/resources/features/**_Case4_DataExtraction_**.feature"," and select "Run".

### 2. **From Terminal**

```sh
mvn test
```

---

## Test Scenarios

- All test scenarios are defined in `.feature` files under `src/test/java/resources/features/`.
- Step definitions are implemented in the `steps/` directory.

---

## Key Classes

- **TestRunner.java:** Main entry point for running tests.
- **Hooks.java:** Handles setup and teardown for each test (e.g., opening/closing browser, taking screenshots).
- **pages/**: Contains page objects and actions (Page Object Model).
- **utilities/**: Configuration, driver management, and helper functions.
- **CSVPage.java:** Saves and analyzes flight data in CSV format.
- **FlightAnalyzer.java:** Performs analysis on flight data.

---

## Reporting

- After running tests, view the HTML report at `target/cucumber-report.html`.

---

## Common Commands

- **Run tests:** `mvn test`
- **Open report:** `open target/cucumber-report.html` (for Mac)

---

## Developer Notes

- **To add a new test:**
  1. Create a new `.feature` file.
  2. Add step definitions in the `steps/` directory.
  3. Create new page or utility classes if needed.
- **Test Data:** Prefer using external sources (CSV, JSON) for test data.
- **Code Standards:** Add comments and keep code readable.

---

## FAQ

- **How do I change the browser?**  
  Edit the `browser` value in the configuration file.

- **Why are tests failing?**

  - The URL or locators may have changed.
  - Test data may be outdated.
  - Dependencies may be missing.

  ## Utility Functions

Below are the main utility functions and helper classes used in this project. These utilities help keep the code clean, reusable, and easy to maintain.

- **convertDurationToMinutes(String duration):**  
  Converts a duration string (e.g., `"2g 3sa 15dk"`) into total minutes as an integer.  
  _Example:_

  ```java
  CSVPage page = new CSVPage();
  int minutes = page.convertDurationToMinutes("1g 2sa 30dk"); // returns 1590
  ```

- **parseConnectionInfo(String info):**  
  Parses the connection information string (e.g., `"1 aktarma"`, `"direkt"`) and returns the number of stops as an integer. Returns -1 if the format is unknown.  
  _Example:_

  ```java
  int stops = page.parseConnectionInfo("1 aktarma"); // returns 1
  ```

- **DriverManager.getDriver():**  
  Returns the current WebDriver instance for browser automation. Ensures a single driver instance is used throughout the test run.  
  _Example:_

  ```java
  WebDriver driver = DriverManager.getDriver();
  ```

- **DriverManager.closeDriver():**  
  Closes and quits the current WebDriver instance, freeing up resources.  
  _Example:_

  ```java
  DriverManager.closeDriver();
  ```

- **ConfigurationReader.getProperty(String key):**  
  Reads a property value from the configuration file (e.g., browser type, base URL).  
  _Example:_

  ```java
  String browser = ConfigurationReader.getProperty("browser");
  ```

- **ScreenShotUtil.takeScreenshot(String fileName):**  
  Takes a screenshot of the current browser window and saves it with the given file name. Useful for debugging failed tests.  
  _Example:_

  ```java
  ScreenShotUtil.takeScreenshot("failed_test");
  ```

- **BrowserUtils.waitFor(int seconds):**  
  Pauses the test execution for the specified number of seconds. Useful for explicit waits in test steps.  
  _Example:_

  ```java
  BrowserUtils.waitFor(5);
  ```

- **FlightAnalyzer.flightGraphs(String fromCity, String toCity):**  
  Generates visual graphs for flight data analysis between two cities.  
  _Example:_

  ```java
  FlightAnalyzer.flightGraphs("istanbul", "ankara");
  ```

- **FlightAnalyzer.paretoOptimal(String fromCity, String toCity):**  
  Performs Pareto optimality analysis on the collected flight data to find the best options.  
  _Example:_
  ```java
  FlightAnalyzer.paretoOptimal("istanbul", "ankara");
  ```

---

## Example Usage

```java
// Convert duration string to minutes
CSVPage page = new CSVPage();
int minutes = page.convertDurationToMinutes("1g 2sa 30dk"); // returns 1590

// Parse connection info
int stops = page.parseConnectionInfo("1 aktarma"); // returns 1

// Get browser driver
WebDriver driver = DriverManager.getDriver();

// Read a property from config
String url = ConfigurationReader.getProperty("url");

// Take a screenshot
ScreenShotUtil.takeScreenshot("error_screenshot");

// Wait for 3 seconds
BrowserUtils.waitFor(3);

// Analyze flight data
FlightAnalyzer.flightGraphs("istanbul", "ankara");
FlightAnalyzer.paretoOptimal("istanbul", "ankara");
```

## Test Strategy

- All critical user journeys are automated using BDD (Cucumber).
- Utility methods are covered with unit tests for edge cases.
- Both positive and negative scenarios are considered.
- Test results are reported in HTML format.
- Screenshots are captured on failure for debugging.
- Test data is managed externally for flexibility.
