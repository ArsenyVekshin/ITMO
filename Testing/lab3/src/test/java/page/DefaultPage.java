package page;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class DefaultPage {

    public final WebDriver driver;
    public final WebDriverWait wait;

    public abstract String getURL();

    @SneakyThrows
    public void open() {
        driver.get(getURL());
        //wait(10000);
    }

    protected WebElement find(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        if (element == null) throw new IllegalArgumentException("Element '" + locator + "' not found");
        if (!element.isDisplayed()) throw new IllegalArgumentException(locator + " is not displayed");

        return element;
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected void click(By locator) {
        WebElement element = find(locator);
        element.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
    }

    protected String getText(By locator) {
        WebElement element = find(locator);
        return element.getText();
    }

    public WebElement browseWholePage() {
        WebElement body = find(By.tagName("body"));
        body.sendKeys(Keys.PAGE_DOWN);
        body.sendKeys(Keys.PAGE_DOWN);
        body.sendKeys(Keys.PAGE_UP);
        body.sendKeys(Keys.PAGE_UP);
        return body;
    }

    protected List<String> getBlockContent(By contentLocator) {
        WebElement container = find(contentLocator); // дожидаемся пока элемент появится
        List<String> out = new ArrayList<>();
        List<WebElement> elements = container.findElements(By.tagName("*"));
        for (WebElement element : elements) {
            out.add(element.getText());
        }
        return out;
    }

    public List<String> checkBlockConsistency(By container, By title, By content) {
        find(container);
        find(title);
        find(content);
        return getBlockContent(content);
    }

    public void typoIntoField(By field, String text) {
        WebElement element = find(field);
        element.clear();
        element.sendKeys(text);
    }


}