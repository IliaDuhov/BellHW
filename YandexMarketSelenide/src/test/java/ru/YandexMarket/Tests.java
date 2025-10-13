package ru.YandexMarket;

import com.codeborne.selenide.WebDriverRunner;
import org.example.page.YandexMarketFirstPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.example.helpers.Properties.testsProperties;

public class Tests extends BaseTests{


    @ParameterizedTest
    @CsvSource({"Электроника, Мобильные телефоны"})
    public void firstTest(String sectionName, String subSectionName){
        open(testsProperties.yandexMarketUrl());
        WebDriverRunner.getWebDriver().manage().window().maximize();
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage();
        yandexMarketFirstPage.openCatalog();
        yandexMarketFirstPage.moveCursorToSection(sectionName);
        yandexMarketFirstPage.moveToSubSection(subSectionName);
    }
}
