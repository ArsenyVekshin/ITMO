package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateProjectTest extends DefaultTest {

    private final String projectName = "sample project";
    private final String projectSlug = "sample-project";

    private void fillProjectFields(boolean readmeflag) {
        projectCreationSettingsPage.setProjectName(projectName);

        String slug = projectCreationSettingsPage.getProjectSlug();
        assertEquals(projectSlug, slug);

        projectCreationSettingsPage.setProjectSlug(projectSlug);
        projectCreationSettingsPage.setProjectDescription("Sample project description");

        projectCreationSettingsPage.chooseVisibilityType("public");

        if (readmeflag) projectCreationSettingsPage.setReadmeFlag();
        projectCreationSettingsPage.submitForm();
    }

    @AfterEach
    public void deleteProject() {
        projectSettingsPage.setURL("https://gitlab.se.ifmo.ru/ArsenyVekshin/" + projectSlug + "/edit");
        projectSettingsPage.open();
        WebElement body = projectSettingsPage.browseWholePage();

        projectSettingsPage.expandAdvancedSettings();
        body.sendKeys(Keys.PAGE_DOWN);

        projectSettingsPage.clickDeleteProjectButton();
        projectSettingsPage.setProjectNameConfirmField(projectSlug);
        projectSettingsPage.clickConfirmedDeleteProjectButton();


        mainPage.open();
        mainPage.browseWholePage();
        try{
            Thread.sleep(5000);
        } catch (Exception e) {}
        mainPage.open();
        assertFalse(mainPage.validateProjectExistence(projectSlug));
    }


    @Test
    public void createBlankProject() {
        createProjectPage.open();
        createProjectPage.browseWholePage();

        createProjectPage.jumpToCreateBlankProject();
        projectCreationSettingsPage.setURL("https://gitlab.se.ifmo.ru/projects/new#blank_project");

        fillProjectFields(true);

        mainPage.open();
        mainPage.browseWholePage();
        assertTrue(mainPage.validateProjectExistence(projectSlug));
    }

    @Test
    public void createTemplateProject() {
        createProjectPage.open();
        createProjectPage.browseWholePage();

        createProjectPage.jumpToCreateTemplateProject();
        List<String> templates = templateProjectCreationPage.getPossibleTemplates();
        assertFalse(templates.isEmpty());

        templateProjectCreationPage.chooseSpringTemplate();

        projectCreationSettingsPage.setURL("https://gitlab.se.ifmo.ru/projects/new#create_from_template");

        assertTrue(projectCreationSettingsPage.isTemplate());
        String template = projectCreationSettingsPage.getChosenTemplate();
        assertEquals("Spring", template);

        fillProjectFields(false);

        mainPage.open();
        mainPage.browseWholePage();
        assertTrue(mainPage.validateProjectExistence(projectSlug));
    }


}
