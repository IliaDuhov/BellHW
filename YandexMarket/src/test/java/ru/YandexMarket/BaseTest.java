package ru.YandexMarket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.example.helpers.Properties.testsProperties;

/**
 * Класс родитель для тестов
 * @author IliaDuhov
 */
public class BaseTest {

    /**
     * Поле для инициализации драйвера
     * @author IliaDuhov
     */
    protected WebDriver chromeDriver;

    /**
     * Метод выполняющий инициализацию драйвера перед каждым тестом
     * @author IliaDuhov
     */
    @BeforeEach
    public void before(){
        System.setProperty("webdriver.chrome.driver", System.getenv(testsProperties.chromeDriverPath()));
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
    }

    /**
     * Метод, закрывающий браузер после каждого теста
     * @author IliaDuhov
     */
    @AfterEach
    public void after(){
        chromeDriver.quit();
    }
}
