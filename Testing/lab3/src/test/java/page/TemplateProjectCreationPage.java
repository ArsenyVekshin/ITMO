package page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Getter
public class TemplateProjectCreationPage extends DefaultPage {

    private final String URL = "https://gitlab.se.ifmo.ru/projects/new#blank_project";
    private final By templatesContainer = By.xpath("//*[@class='project-templates-buttons']");
    private final By springTemplateButton = By.xpath("//*[@for='spring']/span[@data-qa-selector='use_template_button']");
    public TemplateProjectCreationPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public List<String> getPossibleTemplates() {
        return getBlockContent(templatesContainer);
    }

    public void chooseSpringTemplate() {
        click(springTemplateButton);
    }

}
