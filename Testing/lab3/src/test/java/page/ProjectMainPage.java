package page;


import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProjectMainPage extends DefaultPage {

    private final By projectNameField = By.xpath("//*[@data-qa-selector='project_name_content']");
    private final By downloadToolbar = By.xpath("//*[@data-qa-selector='download_source_code_button']");
    private final By downloadZipButton = By.xpath("//*[@href = '/ArsenyVekshin/sample-project1/-/archive/master/sample-project1-master.zip']");
    private final By lastCommitPanel = By.xpath("//*[@class='project-last-commit']");
    private final By lastCommitName = By.xpath("//div[@class='commit-content qa-commit-content']/a");
    private final By lastCommitID = By.xpath("//*[@data-testid='last-commit-id-label']");
    private final By lastPipeline = By.xpath("//*[@class='gl-link js-commit-pipeline']");
    private final By webIdeButton = By.xpath("//*[contains(text(),'Web IDE')]");
    private final By settingsButton = By.xpath("//*[@id='js-onboarding-settings-link']");
    private final By pipelinesButton = By.xpath("//*[@id='js-onboarding-pipelines-link']");
    @Getter
    @Setter
    private String URL = "https://gitlab.se.ifmo.ru/ArsenyVekshin/sample-project1";


    public ProjectMainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getLastCommitMessage() {
        return find(lastCommitName).getText();
    }

    public String getLastCommitID() {
        return find(lastCommitID).getText();
    }

    public void jumpToWebIde() {
        click(webIdeButton);
    }

    public String getLastPipelineLink() {
        return find(lastPipeline).getAttribute("href");
    }

    public void jumpToSettings() {
        click(settingsButton);
    }

    public void jumpToPipeline() {
        click(pipelinesButton);
    }


}
