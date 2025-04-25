package page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class CreateProjectPage extends DefaultPage {
    private final String URL = "https://gitlab.se.ifmo.ru/projects/new";
    private final By selector = By.xpath("//*]");
    private final By blankProjectButton = By.xpath("//*[contains(text(), 'Create blank project')]");
    private final By templateProjectButton = By.xpath("//*[contains(text(), 'Create from template')]");
    private final By importProjectButton = By.xpath("//*[contains(text(), 'Import project')]");
    public CreateProjectPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void jumpToCreateBlankProject() {
        click(blankProjectButton);
    }

    public void jumpToCreateTemplateProject() {
        click(templateProjectButton);
    }

    public void jumpToImportProject() {
        click(importProjectButton);
    }

}
