package ru.YandexMarket;

import org.example.pages.YandexAfterSearch;
import org.example.pages.YandexMarketFirstPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import static org.example.steps.StepsAll.*;
import org.junit.jupiter.params.provider.MethodSource;

import static org.example.helpers.Properties.testsProperties;

public class Tests extends BaseTest{

    @ParameterizedTest
    @MethodSource("org.example.helpers.DataProvider#provideDataCheckingLaptops")
    @DisplayName("Задание 1.4 - проверка YandexMarket")
    public void test(String sectionName, String subSectionName, Double minPrice, Double maxPrice, String brand1,
                     String brand2, Integer minNumbOfElements){
        openSite(testsProperties.yandexMarketUrl(), chromeDriver);
        openCatalog();
        moveCursorOnSection(sectionName);
        moveToSubSection(subSectionName);
        checkCorrectPage(subSectionName);
        setPriceParams(minPrice,maxPrice);
        selectBrands(brand1, brand2);
        checkNumberOfElements(minNumbOfElements);
        checkFilterApplied(minPrice, maxPrice, brand1, brand2);
        getFirstProductAfterSelect();
        searchHeader(getFirstProductAfterSelect());
        checkResultsAfterHeaderSearch(getFirstProductAfterSelect());

    }
}
