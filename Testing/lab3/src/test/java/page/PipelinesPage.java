package page;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PipelinesPage extends DefaultPage {

    private final By pipelinesHistoryUnits = By.xpath("//*[@data-testid='pipeline-table-row']");
    private final By pipelineStatus = By.xpath("//*[@id='js-code-quality-walkthrough']/span");
    private final By pipelineID = By.xpath("//*[@class='pipeline-id']");
    private final By pipelineTimestamp = By.xpath("//*[@class='finished-at d-none d-md-block']/time");
    private final By pipelineRetryButton = By.xpath("//*[@data-qa-selector='pipeline_retry_button']");
    @Getter
    @Setter
    private String URL = "https://gitlab.se.ifmo.ru/";

    public PipelinesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public List<WebElement> getPipelineHistoryUnits() {
        return findElements(pipelinesHistoryUnits);
    }

    public String getLastPipelineStatus() {
        return find(pipelineStatus).getText();
    }

    public String getLastPipelineID() {
        return find(pipelineID).getText();
    }

    public String getLastPipelineTimestamp() {
        return find(pipelineTimestamp).getAttribute("title");
    }

    public void restartLastPipeline() {
        click(pipelineRetryButton);
    }
}
