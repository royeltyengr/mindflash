package com.mindflash.trainerm;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TrainerTest{
    WebDriver driver;
    WebDriverWait wait;
    Logger logger = Logger.getLogger(TrainerTest.class);

    @BeforeTest
    public void setup() throws URISyntaxException{
        logger.info("setting up webdriver and selenium wait");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource("geckodriver");
        try {
            System.setProperty("webdriver.gecko.driver", url.toURI().getPath());
        } catch (Exception e){
            logger.error(e);
        }
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);
    }

    @Test
    public void checkNavigation(){

        logger.info("invoking browser and waiting on initilizaing");

        driver.get("https://devnullcda6a735a5944863b1eda0531db2f507.mftqa.com/tests/qa/login.aspx");
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("homeBtn")));

        logger.info("Clicking on Analytics");

        WebElement analyticsbtn = driver.findElement(By.id("reportsMenuBtn"));
        analyticsbtn.click();


        logger.info("Switching to iframe and clicking on user_group_performance link");

        WebElement iframecl=driver.findElement(By.tagName("iframe"));
        driver.switchTo().frame(iframecl);
        WebElement usergrouppefromlink=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.link:nth-child(3)")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", usergrouppefromlink);

        logger.info("Verifying different counts for in-progress, missed, total etc");

        WebElement inprogress = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("in-progress-count")));
        WebElement missed_deadline = driver.findElement(By.id("missed-deadline-count"));
        WebElement inv_sent = driver.findElement(By.id("invited-count"));
        WebElement att_failed = driver.findElement(By.id("failed-count"));
        WebElement att_passed = driver.findElement(By.id("passed-count"));
        WebElement total_attempts = driver.findElement(By.id("total-attempts"));
        WebElement average_score = driver.findElement(By.id("average-score"));
        WebElement time_trained = driver.findElement(By.id("time-trained"));
        WebElement average_duration=driver.findElement(By.id("average-duration"));
        WebElement time_elapsed=driver.findElement(By.id("time-elapsed"));

        Assert.assertTrue("in progress count should equal to 1", Integer.parseInt(inprogress.getText())==1);
        Assert.assertTrue("missed deadline count should equal to 1", Integer.parseInt(missed_deadline.getText())==1);
        Assert.assertTrue("initation sent should equal", Integer.parseInt(inv_sent.getText())==5);
        Assert.assertTrue("attempts failed count should equal to 1", Integer.parseInt(att_failed.getText())==1);
        Assert.assertTrue("attempts passed count should equal to 1", Integer.parseInt(att_passed.getText())==1);
        Assert.assertTrue("total attempts should equal to 3", Integer.parseInt(total_attempts.getText())==3);
        Assert.assertTrue("avg score  should equal to 50%", average_score.getText().equalsIgnoreCase("50%"));
        Assert.assertTrue("timed trained should equal to 0m", Integer.parseInt(time_trained.getText().replace("m",""))==0);
        Assert.assertTrue("average-duration should equal to 0m", Integer.parseInt(average_duration.getText().replace("m",""))==0);
        Assert.assertTrue("time_elapsed should equal to 0m",Integer.parseInt(time_elapsed.getText().replace("m",""))==0);

        logger.info("Validating the trainee progress");

        WebElement course0progress=driver.findElement(By.cssSelector(".green"));
        WebElement course1progress=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(4) > td:nth-child(3) > span:nth-child(1)"));
        WebElement course2progress=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(3) > td:nth-child(3) > span:nth-child(1)"));
        WebElement course3progress=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(2) > td:nth-child(3) > span:nth-child(1)"));
        WebElement course4progress=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(1) > td:nth-child(3) > span:nth-child(1)"));

        WebElement course0attempt=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(5) > td:nth-child(4)"));
        WebElement course1attempt=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(4) > td:nth-child(4)"));
        WebElement course2attempt=driver.findElement(By.cssSelector("span.link"));
        WebElement course3attempt=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(2) > td:nth-child(4)"));
        WebElement course4attempt=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(1) > td:nth-child(4)"));

        WebElement course0inviteddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(5) > td:nth-child(5)"));
        WebElement course11inviteddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(4) > td:nth-child(5)"));
        WebElement course2inviteddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(3) > td:nth-child(5)"));
        WebElement course3inviteddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(2) > td:nth-child(5)"));
        WebElement course4inviteddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(1) > td:nth-child(5)"));

        WebElement course0completeddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(5) > td:nth-child(6)"));
        WebElement course1completeddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(4) > td:nth-child(6)"));
        WebElement course2completeddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(3) > td:nth-child(6)"));
        WebElement course3completeddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(2) > td:nth-child(6)"));
        WebElement course4completeddate=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(1) > td:nth-child(6)"));


        WebElement course0lastactivity=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(5) > td:nth-child(7)"));
        WebElement course1lastactivity=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(4) > td:nth-child(7)"));
        WebElement course2lastactivity=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(3) > td:nth-child(7)"));
        WebElement course3lastactivity=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(2) > td:nth-child(7)"));
        WebElement course4lastactivity=driver.findElement(By.cssSelector("tr.alt-blue:nth-child(1) > td:nth-child(7)"));

        Assert.assertTrue("verify course0progress is passed 100%",course0progress.getText().equalsIgnoreCase("Passed 100%"));
        Assert.assertTrue("verify course1progress is passed In Prgress%",course1progress.getText().equalsIgnoreCase("In Progress"));
        Assert.assertTrue("verify course2progress is passed 100%",course2progress.getText().equalsIgnoreCase("Invited"));
        Assert.assertTrue("verify course3progress is passed 100%",course3progress.getText().equalsIgnoreCase("In Progress"));
        Assert.assertTrue("verify course4progress is passed 100%",course4progress.getText().equalsIgnoreCase("Invited"));


        Assert.assertTrue("verify course0 attempted is 1",Integer.parseInt(course0attempt.getText())==1);
        Assert.assertTrue("verify course1 attempted is 1",Integer.parseInt(course1attempt.getText())==1);
        Assert.assertTrue("verify course2 attempted is 0",Integer.parseInt(course2attempt.getText())==0);
        Assert.assertTrue("verify course3 attempted is 1",Integer.parseInt(course3attempt.getText())==1);
        Assert.assertTrue("verify course4 attempted is 0",Integer.parseInt(course4attempt.getText())==0);


        Assert.assertTrue("verify course0 invited date is 21-Feb-2018",course0inviteddate.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course1 invited date is 21-Feb-2018",course11inviteddate.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course2 invited date is 21-Feb-2018",course2inviteddate.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course3 invited date is 21-Feb-2018",course3inviteddate.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course4 invited date is 21-Feb-2018",course4inviteddate.getText().equalsIgnoreCase("21-Feb-2018"));

        Assert.assertTrue("verify course0 completeddate is 21-Feb-2018",course0completeddate.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course1 completeddate is empty",course1completeddate.getText().equalsIgnoreCase("-"));
        Assert.assertTrue("verify course2 completeddate is empty%",course2completeddate.getText().equalsIgnoreCase("-"));
        Assert.assertTrue("verify course3 completeddate is empty%",course3completeddate.getText().equalsIgnoreCase("-"));
        Assert.assertTrue("verify course4 completeddate is empty%",course4completeddate.getText().equalsIgnoreCase("-"));

        Assert.assertTrue("verify course0 last activity date is 21-Feb-2018",course0lastactivity.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course1 last activity date is 21-Feb-2018",course1lastactivity.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course2 last activity date is 21-Feb-2018",course2lastactivity.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course3 last activity date is 21-Feb-2018",course3lastactivity.getText().equalsIgnoreCase("21-Feb-2018"));
        Assert.assertTrue("verify course4 last activity date is 21-Feb-2018",course4lastactivity.getText().equalsIgnoreCase("21-Feb-2018"));

    }

    @AfterTest
    public void tearDown(){
        logger.info("closing sessions");
        driver.quit();
    }


}

