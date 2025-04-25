package page;


import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProjectSettingsPage extends DefaultPage {

    private final By projectNameField = By.xpath("//*[@data-qa-selector='project_name_content']");
    private final By advancedSettingsExpandButton = By.xpath("//*[@id='js-project-advanced-settings']/div/button");
    private final By deleteProjectButton = By.xpath("//button/*[contains(text(), 'Delete project')]");
    private final By deleteConfirmField = By.xpath("//*[@id='delete-project-modal-1___BV_modal_body_']/div/input");
    private final By confirmedDeleteButton = By.xpath("//button/*[contains(text(), 'delete project')]");
    @Getter
    @Setter
    private String URL = "https://gitlab.se.ifmo.ru/ArsenyVekshin/sample-project1";

    public ProjectSettingsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    public void expandAdvancedSettings() {
        click(advancedSettingsExpandButton);
    }

    public void clickDeleteProjectButton() {
        click(deleteProjectButton);
    }

    public void setProjectNameConfirmField(String text) {
        typoIntoField(deleteConfirmField, text);
    }

    public void clickConfirmedDeleteProjectButton() {
        click(confirmedDeleteButton);
    }


}
