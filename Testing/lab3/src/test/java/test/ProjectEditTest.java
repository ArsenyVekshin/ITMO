package test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProjectEditTest extends DefaultTest {

    private final String projectName = "sample-project-2";
    private final String newReadmeData = "meow meow, meow?\n";
    private final String commitMessage = "commit test 1";

    @Test
    public void webIdeTest() {

        projectMainPage.setURL("https://gitlab.se.ifmo.ru/ArsenyVekshin/" + projectName);
        projectMainPage.open();
        projectMainPage.browseWholePage();

        String lastCommitID = projectMainPage.getLastCommitID();
        String lastPipeline = projectMainPage.getLastPipelineLink();
        projectMainPage.jumpToWebIde();

        webIDEPage.setURL("https://gitlab.se.ifmo.ru/-/ide/project/ArsenyVekshin/" + projectName + "/edit/master/-/");

        List<String> files = webIDEPage.getFilesList();
        assertFalse(files.isEmpty());

        webIDEPage.chooseReadmeFile();
        webIDEPage.typoInLastLineOfFile(newReadmeData);

        webIDEPage.startCreatingCommit();
        webIDEPage.chooseCommitType();
        webIDEPage.setCommitMessage(commitMessage);

        webIDEPage.endCreatingCommit();

        projectMainPage.open();
        projectMainPage.browseWholePage();
        assertNotEquals(lastCommitID, projectMainPage.getLastCommitID());
        assertNotEquals(lastPipeline, projectMainPage.getLastPipelineLink());
    }


    @SneakyThrows
    @Test
    public void testPipelines() {
        projectMainPage.setURL("https://gitlab.se.ifmo.ru/ArsenyVekshin/" + projectName);
        projectMainPage.open();
        projectMainPage.browseWholePage();

        projectMainPage.jumpToPipeline();
        pipelinesPage.setURL("https://gitlab.se.ifmo.ru/ArsenyVekshin/" + projectName + "/-/pipelines");

        List<WebElement> pipelines = pipelinesPage.getPipelineHistoryUnits();
        assertFalse(pipelines.isEmpty());

        String lastPipelineStatus = pipelinesPage.getLastPipelineStatus();
        String lastPipelineID = pipelinesPage.getLastPipelineID();
        String lastPipelineTimestamp = pipelinesPage.getLastPipelineTimestamp();

        pipelinesPage.restartLastPipeline();
        //Thread.sleep(10000);
        //pipelinesPage.open();
        //assertFalse(lastPipelineStatus == pipelinesPage.getLastPipelineStatus());

        //while(lastPipelineTimestamp == pipelinesPage.getLastPipelineTimestamp()) {}
        Thread.sleep(60000);

        assertNotEquals(lastPipelineTimestamp, pipelinesPage.getLastPipelineTimestamp());
    }

}
