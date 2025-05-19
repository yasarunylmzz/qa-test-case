# Enuygun QA Automation Test Project

This project provides end-to-end automation tests for the https://wwww.enuygun.com/ web application using Cucumber, JUnit, and Selenium WebDriver. Tests are written following the BDD (Behavior Driven Development) approach.

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
│       └── resource/
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

- Edit the configuration file at `src/test/java/resource/config/properties` to set browser and URL:
  ```
  browser=chrome
  url=https://www.enuygun.com/
  ```

---

## Running the Tests

### 1. **From IDE**

- Right-click on `TestRunner.java`
- Edit @CucumberOptions --> edit this "features = "/Users/yasarunyilmaz/IdeaProjects/com.testcase/src/test/java/resource/features/**_Case4_DataExtraction_**.feature"," and select "Run".

### 2. **From Terminal**

```sh
mvn test
```

---

## Test Scenarios

- All test scenarios are defined in `.feature` files under `src/test/java/resource/features/`.
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
