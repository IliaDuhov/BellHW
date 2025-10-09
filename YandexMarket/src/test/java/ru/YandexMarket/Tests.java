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

/**
 * Класс с тестом
 * @author IliaDuhov
 */
public class Tests extends BaseTest{

    /**
     * Метод выолняющий тест
     * @author IliaDuhov
     * @param sectionName название раздела
     * @param subSectionName название подраздела
     * @param minPrice минимальная цена
     * @param maxPrice максимальная цена
     * @param brands бренды
     * @param minNumbOfElements минимальное количество элементов на странице
     */
    @ParameterizedTest
    @MethodSource("org.example.helpers.DataProvider#provideDataCheckingLaptops")
    @DisplayName("Задание 1.4 - проверка YandexMarket")
    public void test(String sectionName, String subSectionName, Double minPrice, Double maxPrice,
                     Integer minNumbOfElements, String...brands) throws InterruptedException {
        openSite(testsProperties.yandexMarketUrl(), chromeDriver);
        openCatalog();
        moveCursorOnSection(sectionName);
        moveToSubSection(subSectionName);
        checkCorrectPage(subSectionName);
        setPriceParams(minPrice,maxPrice);
        selectBrands(brands);
        checkNumberOfElements(minNumbOfElements);
        checkFilterApplied(minPrice, maxPrice, brands);
        String firstProductAfterSelect = getFirstProductAfterSelect();
        searchHeader(firstProductAfterSelect);
        checkResultsAfterHeaderSearch(firstProductAfterSelect);

    }
}
