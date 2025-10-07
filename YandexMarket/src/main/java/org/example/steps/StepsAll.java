package org.example.steps;

import io.qameta.allure.Step;
import org.example.helpers.Assertions;
import org.example.pages.YandexAfterSearch;
import org.example.pages.YandexMarketFirstPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс, хранящий в себе шаги теста
 * @author IliaDuhov
 */
public class StepsAll {

    /**
     * Поле для инициализации драйвера
     * @author IliaDuhov
     */
    private static WebDriverWait wait;

    /**
     * Поле для инициализации ожиданий
     * @author IliaDuhov
     */
    private static WebDriver chromeDriver;

    /**
     * Метод открытия сайта
     * @param url url сайта
     * @param currentDriver используемый драйвер
     */
    @Step("Переходим на сайт: {url}")
    public static void openSite(String url, WebDriver currentDriver){
        chromeDriver=currentDriver;
        chromeDriver.get(url);
        wait = new WebDriverWait(chromeDriver,30);
    }

    /**
     * Метод открытия каталога
     * @author IliaDuhov
     */
    @Step("Открываем каталог")
    public static void openCatalog(){
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage(chromeDriver);
        yandexMarketFirstPage.openCatalog();
    }

    /**
     * Метод наведения курсора на выбранный раздел
     * @author IliaDuhov
     * @param sectionName название раздела
     */
    @Step("Наводим курсор на раздел: {sectionName}")
    public static void moveCursorOnSection(String sectionName){
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage(chromeDriver);
        yandexMarketFirstPage.moveCursorOnSection(sectionName);
    }

    /**
     * Метод перехода к подразделу
     * @author IliaDuhov
     * @param subSectionName название подраздела
     */
    @Step("Наводим курсор на подраздел: {subSectionName}")
    public static void moveToSubSection(String subSectionName){
        YandexMarketFirstPage yandexMarketFirstPage = new YandexMarketFirstPage(chromeDriver);
        yandexMarketFirstPage.moveToSubSection(subSectionName);
    }

    /**
     * Метод, проверяющий корректность страницы после поиска
     * @author IliaDuhov
     * @param subSectionName название подраздела
     * @return boolean
     */
    @Step("Проверяем на корректной ли мы странице")
    public static void checkCorrectPage(String subSectionName){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        Assertions.assertTrue(yandexAfterSearch.checkCorrectPage(subSectionName), "не корректная страница");
    }

    /**
     * Метод, устанавливабщий значение минимальной и максимальной цены продукта
     * @author IliaDuhov
     * @param minPrice минимальная цена
     * @param maxPrice максимальная цена
     */
    @Step("Устанавливаем фильтр цен: {minPrice}, {maxPrice}")
    public static void setPriceParams(Double minPrice, Double maxPrice){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        yandexAfterSearch.setPriceParams(minPrice,maxPrice);
    }

    /**
     * Метод, устанавливающий бренд продукта. Если бренды есть в начальном меню, то они выбираются. Если брендов нет,
     * производится поиск по всем брендам
     * @author IliaDuhov
     * @param brands бренды
     */
    @Step("Выбор производителей: {brand1}, {brand2}")
    public static void selectBrands(String...brands) {
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        yandexAfterSearch.selectBrands(brands);
    }

    /**
     * Метод, проверяющий количество элементов после применения фильтров
     * @author IliaDuhov
     * @param minNumbOfElements минимальное количество элементов
     * @return boolean
     */
    @Step("Проверяем количество товаров: {minNumbOfElements}")
    public static void checkNumberOfElements(Integer minNumbOfElements){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        Assertions.assertTrue(yandexAfterSearch.
                checkNumberOfElements(minNumbOfElements), "Количество найденных элементов не сооветсвует "+ minNumbOfElements);
    }

    /**
     * Метод, проверяющий применились ли циклы ко всем элементам
     * @author IliaDuhov
     * @param minPrice минимальная цена
     * @param maxPrice максимальная цена
     * @param brands бренды
     */
    @Step("Проверяем применились ли фильтры: {minPrice}, {maxPrice}, {brand1}, {brand2}")
    public static void checkFilterApplied(Double minPrice, Double maxPrice, String...brands){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        yandexAfterSearch.checkFilterApplied(minPrice, maxPrice, brands);
    }

    /**
     * Метод поиска продукта
     * @param productTitle полное название продукта
     */
    @Step("Поиск элемента после фильтрации")
    public static void searchHeader(String productTitle){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        yandexAfterSearch.searchHeader(yandexAfterSearch.getFirstProductAfterSelect());
    }

    /**
     * Метод, запоминающий названия первого продукта в ленте
     * @author IliaDuhov
     * @return String
     */
    @Step("Поиск названия продукта")
    public static String getFirstProductAfterSelect(){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        return yandexAfterSearch.getFirstProductAfterSelect();
    }

    /**
     * Метод, проверяющий наличие элемента после поиска
     * @author IliaDuhov
     * @param firstProductAfterSelect ожидаемое название продукта
     * @return boolean
     */
    @Step("Проверка поиска товара после поиска: {firstProductAfterSelect}")
    public static void checkResultsAfterHeaderSearch(String firstProductAfterSelect){
        YandexAfterSearch yandexAfterSearch = new YandexAfterSearch(chromeDriver);
        Assertions.assertTrue(yandexAfterSearch.checkResultsAfterHeaderSearch(yandexAfterSearch.
                getFirstProductAfterSelect()), firstProductAfterSelect + "не найден после поиска");

    }

}
