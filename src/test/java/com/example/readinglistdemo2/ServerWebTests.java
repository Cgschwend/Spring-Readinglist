package com.example.readinglistdemo2;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ReadingListDemo2Application.class)

public class ServerWebTests {

    private static ChromeDriver browser;
    private static WebDriver driver;

    @Test
    public void testChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:");
        assertThat(driver.getTitle(), is(equalTo("localhost")));
        driver.quit();
    }


    @Value("${local.server.port}")

    private int port;

    @BeforeClass
    public static void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:");
        assertThat(driver.getTitle(), is(equalTo("localhost")));
        browser = new ChromeDriver();
        browser.manage().timeouts()
                .implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void addBookToEmptyList() {
        String baseUrl = "http://localhost:" + port;

        browser.get(baseUrl);

        assertEquals("You have no books in your book list",
                browser.findElementByTagName("div").getText());

        browser.findElementByName("title")
                .sendKeys("BOOK TITLE");
        browser.findElementByName("author")
                .sendKeys("BOOK AUTHOR");
        browser.findElementByName("isbn")
                .sendKeys("1234567890");
        browser.findElementByName("description")
                .sendKeys("DESCRIPTION");
        browser.findElementByTagName("form")
                .submit();

        WebElement dl =
                browser.findElementByCssSelector("dt.bookHeadline");
        assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)", dl.getText());

        WebElement dt =
                browser.findElementByCssSelector("dd.bookDescription");
        assertEquals("DESCRIPTION", dt.getText());
    }
}
