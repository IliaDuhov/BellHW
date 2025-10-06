package ru.YandexMarket;

import org.example.pages.YandexAfterSearch;
import org.example.pages.YandexMarketFirstPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.example.helpers.Properties.testsProperties;

public class Tests extends BaseTest{

    @ParameterizedTest
    @MethodSource("org.example.helpers.DataProvider#provideDataCheckingLaptops")
    @DisplayName("Задание 1.4 - проверка YandexMarket")
    public void test(String sectionName, String subSectionName, Double minPrice, Double maxPrice, String brand1,
                     String brand2, Integer minNumbOfElements){
        chromeDriver.get(testsProperties.yandexMarketUrl());
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage(chromeDriver);
        yandexMarketFirstPage.openCatalog();
        yandexMarketFirstPage.moveCursorOnSection(sectionName);
        yandexMarketFirstPage.moveToSubSection(subSectionName);
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        Assertions.assertTrue(yandexAfterSearch.checkCorrectPage(subSectionName));
        yandexAfterSearch.setPriceParams(minPrice,maxPrice);
        yandexAfterSearch.selectBrands(brand1, brand2);
        Assertions.assertTrue(yandexAfterSearch.checkNumberOfElements(minNumbOfElements),
                "меньше элементов, чем " + minNumbOfElements);
        yandexAfterSearch.checkFilterApplied(minPrice, maxPrice, brand1, brand2);
        yandexAfterSearch.searchHeader(yandexAfterSearch.getFirstProductAfterSelect());
        Assertions.assertTrue(yandexAfterSearch.
                checkResultsAfterHeaderSearch(yandexAfterSearch.getFirstProductAfterSelect()));

    }


}
