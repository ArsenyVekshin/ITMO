package page;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class WebIDEPage extends DefaultPage {


    private final By filesListContainer = By.xpath("//*[@data-testid='ide-tree-body']");
    private final By readmeFileSelector = By.xpath("//*[contains(text(), 'README.md')]");
    private final By fileLinesFields = By.xpath("//div[@class='view-lines']/*[@class='view-line']/span/span");
    private final By newTextCollector = By.xpath("//textarea[@class='inputarea']");
    private final By commitButton = By.xpath("//*[@data-testid='begin-commit-button']");
    private final By createMRButton = By.xpath("//*[@class='mb-0 js-ide-commit-new-mr']");
    private final By commitTypeRadio = By.xpath("//*[@data-qa-selector='commit_type_radio']");
    private final By commitMessageField = By.xpath("//*[@data-qa-selector='ide_commit_message_field']");
    private final By finalCommitButton = By.xpath("//*[@data-testid='commit-button-tooltip']");
    @Getter
    @Setter
    private String URL = "https://gitlab.se.ifmo.ru/";


    public WebIDEPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public List<String> getFilesList() {
        return driver.findElements(filesListContainer).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void chooseReadmeFile() {
        click(readmeFileSelector);
    }

    public void typoInLastLineOfFile(String text) {
        List<WebElement> lines = findElements(fileLinesFields);
        WebElement line = lines.get(lines.size() - 1);
        line.click();

        WebElement textarea = driver.findElement(newTextCollector);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                textarea, text
        );
    }

    public void chooseCommitType() {
        click(commitTypeRadio);
    }

    public void setCommitMessage(String text) {
        click(commitMessageField);
        typoIntoField(commitMessageField, text);
    }

    public void startCreatingCommit() {
        click(commitButton);
    }

    public void endCreatingCommit() {
        click(finalCommitButton);
    }

}
