package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.*;

import java.time.Duration;

public class DefaultTest {

    private final String login = "ArsenyVekshin";
    private final String password = "password";
    protected WebDriver driver;
    protected MainPage mainPage;
    protected ProjectCreationSettingsPage projectCreationSettingsPage;
    protected TemplateProjectCreationPage templateProjectCreationPage;
    protected CreateProjectPage createProjectPage;
    protected AuthPage authPage;
    protected ProjectSettingsPage projectSettingsPage;
    protected WebIDEPage webIDEPage;
    ProjectMainPage projectMainPage;
    PipelinesPage pipelinesPage;

    @BeforeAll
    static void setupClass() {
        String browser = System.getenv("BROWSER");
        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
        } else {
            WebDriverManager.chromedriver().setup();
        }
    }

    // other pages

    @SneakyThrows
    void authorize() {
        authPage.open();
        authPage.browseWholePage();

        authPage.setUsername(login);
        authPage.setPassword(password);
        authPage.setRememberMe(true);

        authPage.submit();
    }

    @BeforeEach
    void setupTest() {
        String browser = System.getenv("BROWSER");
        if (true) {
            FirefoxOptions options = new FirefoxOptions();
            driver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            // Антидетект
            options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage",
                    "--disable-background-networking", "--disable-ipc-flooding-protection",
                    "--disable-renderer-backgrounding", "--disable-background-timer-throttling",
                    "--remote-allow-origins=*");

            driver = new ChromeDriver(options);
            ((JavascriptExecutor) driver).executeScript(
                    "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
            );
        }

        driver.manage().window().maximize();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        mainPage = new MainPage(driver, wait);
        projectCreationSettingsPage = new ProjectCreationSettingsPage(driver, wait);
        templateProjectCreationPage = new TemplateProjectCreationPage(driver, wait);
        createProjectPage = new CreateProjectPage(driver, wait);
        authPage = new AuthPage(driver, wait);
        projectSettingsPage = new ProjectSettingsPage(driver, wait);
        webIDEPage = new WebIDEPage(driver, wait);
        projectMainPage = new ProjectMainPage(driver, wait);
        pipelinesPage = new PipelinesPage(driver, wait);


        try {
            mainPage.open();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='login-body']")));
            authorize(); // если элемент найден, вызываем авторизацию
        } catch (Exception e) {
            // Элемент не найден — значит, мы не на странице авторизации
        }
        // other pages

    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.close();
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
        }
    }
}
