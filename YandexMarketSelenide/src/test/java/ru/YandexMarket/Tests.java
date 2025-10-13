package ru.YandexMarket;

import com.codeborne.selenide.WebDriverRunner;
import org.example.helpers.Assertions;
import org.example.page.YandexAfterSearchPage;
import org.example.page.YandexMarketFirstPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static com.codeborne.selenide.Selenide.open;
import static org.example.helpers.Properties.testsProperties;

public class Tests extends BaseTests{


    @ParameterizedTest
    @MethodSource("org.example.helpers.DataProvider#provideDataCheckingLaptops")
    @DisplayName("Задание 2.1 - проверка YandexMarket Selenide")
    public void firstTest(String sectionName, String subSectionName, String...brands){
        open(testsProperties.yandexMarketUrl());
        WebDriverRunner.getWebDriver().manage().window().maximize();
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage();
        yandexMarketFirstPage.openCatalog();
        yandexMarketFirstPage.moveCursorToSection(sectionName);
        yandexMarketFirstPage.moveToSubSection(subSectionName);
        YandexAfterSearchPage yandexAfterSearchPage = new YandexAfterSearchPage();
        yandexAfterSearchPage.checkIfPageCorrect(subSectionName);
        yandexAfterSearchPage.selectBrands(brands);
        yandexAfterSearchPage.checkFilterApplied(brands);
    }
}
