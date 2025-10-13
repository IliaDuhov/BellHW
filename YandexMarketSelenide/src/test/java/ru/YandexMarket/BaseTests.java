package ru.YandexMarket;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.example.helpers.CustomAllureSelenide;
import org.example.page.BasePage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.example.helpers.Properties.testsProperties;

public class BaseTests {

    @BeforeAll
    public static void setup(){
        //SelenideLogger.addListener("AllureSelenide",new CustomAllureSelenide().screenshots(true)
        //        .savePageSource(true));

        Configuration.timeout = testsProperties.defaultTimeout();
        Configuration.browser = testsProperties.chromeDriverName();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        Configuration.browserCapabilities = capabilities;
    }
}