package page;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProjectCreationSettingsPage extends DefaultPage {

    public final By projectNameField = By.xpath("//input[@id='project_name']");
    public final By projectSlugField = By.xpath("//input[@id='project_path']");
    public final By projectDescriptionField = By.xpath("//textarea[@id='project_description']");
    public final By projectVisibilityPrivateButton = By.xpath("//input[@id='project_visibility_level_0']");
    public final By projectVisibilityInternalButton = By.xpath("//input[@id='project_visibility_level_10']");
    public final By projectVisibilityPublicButton = By.xpath("//input[@id='project_visibility_level_20']");
    public final By createReadmeButton = By.xpath("//*[contains(text(), 'README')]");
    public final By submitButton = By.xpath("//*[@name='commit']");
    private final By templateContainer = By.xpath("//*[@class='input-group template-input-group']");
    private final By templateField = By.xpath("//*[@class='selected-template']");
    @Getter
    @Setter
    private String URL = "https://gitlab.se.ifmo.ru/projects/new";

    public ProjectCreationSettingsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void setProjectName(String text) {
        typoIntoField(projectNameField, text);
    }

    public String getProjectSlug() {
        WebElement element = find(projectSlugField);
        return element.getAttribute("value");
    }

    public void setProjectSlug(String text) {
        typoIntoField(projectSlugField, text);
    }

    public void setProjectDescription(String text) {
        typoIntoField(projectDescriptionField, text);
    }

    public void chooseVisibilityType(String type) {
        if (type.equals("private")) {
            click(projectVisibilityPrivateButton);
        } else if (type.equals("internal")) {
            click(projectVisibilityInternalButton);
        } else if (type.equals("public")) {
            click(projectVisibilityPublicButton);
        }
    }

    public void setReadmeFlag() {
        click(createReadmeButton);
    }

    public void submitForm() {
        click(submitButton);
    }


    public boolean isTemplate() {
        return find(templateContainer).isDisplayed();
    }

    public String getChosenTemplate() {
        if (!isTemplate()) throw new IllegalStateException("Template is not selected");
        return find(templateField).getText();
    }
}
