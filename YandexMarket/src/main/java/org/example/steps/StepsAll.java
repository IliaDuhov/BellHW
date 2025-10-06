package org.example.steps;

import io.qameta.allure.Step;
import org.example.helpers.Assertions;
import org.example.pages.YandexAfterSearch;
import org.example.pages.YandexMarketFirstPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepsAll {

    private static WebDriverWait wait;
    private static WebDriver chromeDriver;

    @Step("Переходим на сайт: {url}")
    public static void openSite(String url, WebDriver currentDriver){
        chromeDriver=currentDriver;
        chromeDriver.get(url);
        wait = new WebDriverWait(chromeDriver,30);
    }

    @Step("Открываем каталог")
    public static void openCatalog(){
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage(chromeDriver);
        yandexMarketFirstPage.openCatalog();
    }

    @Step("Наводим курсор на раздел: {sectionName}")
    public static void moveCursorOnSection(String sectionName){
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage(chromeDriver);
        yandexMarketFirstPage.moveCursorOnSection(sectionName);
    }

    @Step("Наводим курсор на подраздел: {subSectionName}")
    public static void moveToSubSection(String subSectionName){
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage(chromeDriver);
        yandexMarketFirstPage.moveToSubSection(subSectionName);
    }

    @Step("Проверяем на корректной ли мы странице")
    public static void checkCorrectPage(String subSectionName){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        Assertions.assertTrue(yandexAfterSearch.checkCorrectPage(subSectionName), "не корректная страница");
    }

    @Step("Устанавливаем фильтр цен: {minPrice}, {maxPrice}")
    public static void setPriceParams(Double minPrice, Double maxPrice){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        yandexAfterSearch.setPriceParams(minPrice,maxPrice);
    }

    @Step("Выбор производителей: {brand1}, {brand2}")
    public static void selectBrands(String brand1, String brand2) {
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        yandexAfterSearch.selectBrands(brand1, brand2);
    }

    @Step("Проверяем количество товаров: {minNumbOfElements}")
    public static void checkNumberOfElements(Integer minNumbOfElements){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        Assertions.assertTrue(yandexAfterSearch.
                checkNumberOfElements(minNumbOfElements), "Количество найденных элементов не сооветсвует "+ minNumbOfElements);
    }

    @Step("Проверяем применились ли фильтры: {minPrice}, {maxPrice}, {brand1}, {brand2}")
    public static void checkFilterApplied(Double minPrice, Double maxPrice, String brand1, String brand2){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        yandexAfterSearch.checkFilterApplied(minPrice, maxPrice, brand1, brand2);
    }

    @Step("Поиск элемента после фильтрации")
    public static void searchHeader(String productTitle){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        yandexAfterSearch.searchHeader(yandexAfterSearch.getFirstProductAfterSelect());
    }

    @Step("Поиск названия продукта")
    public static String getFirstProductAfterSelect(){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        return yandexAfterSearch.getFirstProductAfterSelect();
    }

    @Step("Проверка поиска товара после поиска: {firstProductAfterSelect}")
    public static void checkResultsAfterHeaderSearch(String firstProductAfterSelect){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        Assertions.assertTrue(yandexAfterSearch.checkResultsAfterHeaderSearch(yandexAfterSearch.
                getFirstProductAfterSelect()), firstProductAfterSelect + "не найден после поиска");

    }

}
