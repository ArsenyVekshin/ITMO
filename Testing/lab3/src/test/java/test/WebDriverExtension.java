package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;
import java.util.stream.Stream;

public class WebDriverExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(
                createInvocationContext("chrome"),
                createInvocationContext("firefox")
        );
    }

    private TestTemplateInvocationContext createInvocationContext(String browser) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "Browser: " + browser;
            }

            public void beforeEach(ExtensionContext context) {
                WebDriver driver;
                if ("chrome".equalsIgnoreCase(browser)) {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-blink-features=AutomationControlled");
                    options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
                    options.setExperimentalOption("useAutomationExtension", false);
                    options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
                    driver = new ChromeDriver(options);
                } else {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options = new FirefoxOptions();
                    driver = new FirefoxDriver(options);
                }

                context.getStore(ExtensionContext.Namespace.GLOBAL).put("driver", driver);
            }

            public void afterEach(ExtensionContext context) {
                WebDriver driver = context.getStore(ExtensionContext.Namespace.GLOBAL).get("driver", WebDriver.class);
                if (driver != null) {
                    driver.quit();
                }
            }
        };
    }
}
