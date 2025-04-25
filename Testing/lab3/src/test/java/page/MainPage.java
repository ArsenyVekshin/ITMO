package page;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class MainPage extends DefaultPage {

    /*
    WebElement header = find(By.tagName("header"));
    WebElement body = find(By.tagName("body"));
    */

    private final String URL = "https://gitlab.se.ifmo.ru/";
    private final By projectsContainer = By.xpath("//*[@class='projects-list']");
    private final By projectRow = By.xpath("//*[@class='project-row']");
    private final By projectsNavYourProjects = By.xpath("//*[contains(@href, '/dashboard/projects']");
    private final By projectsNavStarredProjects = By.xpath("//*[contains(@href, '/dashboard/projects']");
    private final By projectsNavExploreProjects = By.xpath("//*[contains(@href, '/explore']");
    private final By projectsFilterPersonal = By.xpath("//*[contains(@href, '/dashboard/projects?personal=true&sort=name_asc']");
    private final By NewProjectsButton = By.xpath("//*[contains(@href, '/projects/new']");


    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


}
