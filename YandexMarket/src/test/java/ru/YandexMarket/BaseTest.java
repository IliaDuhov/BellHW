package ru.YandexMarket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    protected WebDriver chromeDriver;

    @BeforeEach
    public void before(){
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
    }

    @AfterEach
    public void after(){
        chromeDriver.quit();
    }
}
