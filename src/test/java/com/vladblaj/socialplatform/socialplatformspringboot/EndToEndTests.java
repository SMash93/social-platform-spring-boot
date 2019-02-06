package com.vladblaj.socialplatform.socialplatformspringboot;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.openqa.selenium.chrome.ChromeDriverService.createDefaultService;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndTests {
    static ChromeDriverService service;
    static ChromeDriver driver;
    @LocalServerPort
    int port;

    @Autowired
    MongoOperations operations;

    @BeforeClass
    public static void setUp() throws IOException {
        System.setProperty("webdriver.chrome.driver", "ext\\chromedriver.exe");
        service = createDefaultService();
        driver = new ChromeDriver(service);
        Path testResults = Paths.get("build", "test-results");
        if (!Files.exists(testResults)) {
            Files.createDirectory(testResults);
        }

    }

    @AfterClass
    public static void tearDown() {
        service.stop();
    }

    private String pathToQuestions = "F:\\Teste OCA\\Objective-Wise\\Lambda Expressions\\Questions\\";
    private String pathToAnswers = "F:\\Teste OCA\\Objective-Wise\\Lambda Expressions\\Answers\\";

    @Test
    public void homePageShouldWork() throws IOException {
        int questionNumber = 1;
        driver.get("http://webets-server-aws.enthuware.com/webets.html");
        driver.findElement(By.cssSelector("input#login-username")).sendKeys("lorena.pojar@msg.group");
        driver.findElement(By.cssSelector("input#login-password")).sendKeys("180806");
        driver.findElement(By.xpath("//button[contains(., 'Login')]")).click();

        //wait for me
        while (questionNumber <= 5) {
            saveQuestion(questionNumber);
            navigateToNextPage();
            questionNumber++;
        }

    }

    private void navigateToNextPage() {
        driver.findElement(By.xpath("//button[contains(., 'Next')]")).click();
    }

    private void takeScreenshot(String path, int questionNumber) throws IOException {
        FileCopyUtils.copy(
                driver.getScreenshotAs(OutputType.FILE),
                new File(path + "Question-" + questionNumber + ".png"));
    }

    private void saveQuestion(int questionNumber) throws IOException {
        takeScreenshot(pathToQuestions, questionNumber);
        driver.findElement(By.xpath("//button[contains(., 'Evaluate')]")).click();
        takeScreenshot(pathToAnswers, questionNumber);
    }
}