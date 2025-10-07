package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляющий собой страницу после поиска
 * @author IliaDuhov
 */
public class YandexAfterSearch extends YandexMarketFirstPage {

    /**
     * Конструтор, обращающийся к конструктору родителя YandexMarketFirstPage.java
     * @author IliaDuhov
     * @param chromeDriver
     */
    public YandexAfterSearch(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    /**
     * Метод, проверяющий корректность страницы после поиска
     * @author IliaDuhov
     * @param subSection название подраздела
     * @return boolean
     */
    public boolean checkCorrectPage(String subSection) {
        List<WebElement> header = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                " //h1[text()='" + subSection + "']"
        )));
        return !header.isEmpty();
    }

    /**
     * Метод, устанавливабщий значение минимальной и максимальной цены продукта
     * @author IliaDuhov
     * @param minPrice минимальная цена
     * @param maxPrice максимальная цена
     */
    public void setPriceParams(Double minPrice, Double maxPrice) {
        WebElement minPriceInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//input[@type='text' and contains(@id, 'glprice_25563_min')]"
        )));
        minPriceInput.clear();
        minPriceInput.sendKeys(minPrice.toString());

        WebElement maxPriceInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//input[@type='text' and contains(@id, 'glprice_25563_max')]"
        )));
        maxPriceInput.clear();
        maxPriceInput.sendKeys(maxPrice.toString());
    }

    /**
     * Метод, устанавливающий бренд продукта. Если бренды есть в начальном меню, то они выбираются. Если брендов нет,
     * производится поиск по всем брендам
     * @author IliaDuhov
     * @param brands бренды
     */
    public void selectBrands(String... brands) {
        WebElement brandSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@data-auto='filter' and contains(., 'Бренд')]//*[contains(text(), 'Бренд')]")
        ));

        List<String> selectedBrands = new ArrayList<>();

        for (String brand : brands) {
            List<WebElement> foundBrands = chromeDriver.findElements(By.xpath(
                    "//div[@data-auto='filter' and contains(., 'Бренд')]//span[contains(text(), '" + brand + "')]"
            ));
            if (!foundBrands.isEmpty()) {
                WebElement brandCheckbox = wait.until(ExpectedConditions.elementToBeClickable(foundBrands.get(0)));
                brandCheckbox.click();
                selectedBrands.add(brand);
            }
        }

        if (selectedBrands.size() < brands.length) {
            WebElement allBrands = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//div[@data-auto='filter' and contains(., 'Бренд')]//div[@data-baobab-name='showMoreFilters']//button"
            )));
            allBrands.click();

            WebElement brandToFind = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//fieldset//input[@placeholder='Найти']"
            )));

            for (String brand : brands) {
                if (!selectedBrands.contains(brand)) {
                    brandToFind.clear();
                    brandToFind.sendKeys(brand);

                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//fieldset//label[@role='checkbox']//span[contains(text(), '" + brand + "')]"
                    )));

                    WebElement brandCheckbox = chromeDriver.findElement(By.xpath(
                            "//fieldset//label[@role='checkbox']//span[contains(text(), '" + brand + "')]"
                    ));
                    wait.until(ExpectedConditions.elementToBeClickable(brandCheckbox)).click();

                    selectedBrands.add(brand);
                }
            }
        }
    }

    /**
     * Метод, проверяющий количество элементов после применения фильтров
     * @author IliaDuhov
     * @param minCount минимальное количество элементов
     * @return boolean
     */
    public boolean checkNumberOfElements(Integer minCount){
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//a[contains(@href, '/card/') and @tabindex='-1']"
        )));

        return products.size()>=minCount;
    }

    /**
     * Метод, проверяющий применились ли циклы ко всем элементам
     * @author IliaDuhov
     * @param minPrice минимальная цена
     * @param maxPrice максимальная цена
     * @param brands бренды
     */
    public void checkFilterApplied(Double minPrice, Double maxPrice, String... brands) {
        List<WebElement> products = wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath((
                "//a[contains(@href, '/card/') and @tabindex='-1']"
        )))));

        List<WebElement> filteredProducts = new ArrayList<>();

        for (WebElement product : products) {
            String productText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//div[contains(@id, '/content/page')]//span[@tabindex='0']"))).getText();
            String productPrice = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//div[@tabindex='0']//span[contains(@class, 'headline-5_bold')]"
            ))).getText();
            double price = Double.parseDouble(productPrice.replaceAll("[^\\d]", ""));
            boolean priceMatch = price >= minPrice && price <= maxPrice;

            boolean brandMatch = false;
            for (String brand : brands) {
                if (productText.toLowerCase().contains(brand.toLowerCase())) {
                    brandMatch = true;
                    break;
                }
            }

            if (priceMatch && brandMatch) {
                filteredProducts.add(product);
            }
        }
    }

    /**
     * Метод, запоминающий названия первого продукта в ленте
     * @author IliaDuhov
     * @return String
     */
    public String getFirstProductAfterSelect(){
        List<WebElement> productsAfterSelect = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[contains(@id, '/content/page')]//span[@tabindex='0']"
        )));
        return productsAfterSelect.get(0).getText();
    }


    /**
     * Метод поиска продукта
     * @param input полное название продукта
     */
    public void searchHeader(String input){
        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//input[@id='header-search']"
        )));
        inputField.sendKeys(input);
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//button[@type='submit']"
        )));
        searchButton.click();
    }

    /**
     * Метод, проверяющий наличие элемента после поиска
     * @author IliaDuhov
     * @param expectedProduct ожидаемое название продукта
     * @return boolean
     */
    public boolean checkResultsAfterHeaderSearch(String expectedProduct) {
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//a[contains(@href, '/card/') and @tabindex='-1']"
        )));

        for (WebElement product : products) {
            String productText = product.findElement(By.xpath("//div[contains(@id, '/content/page')]//span[@tabindex='0']")).getText();
            if (productText.contains(expectedProduct)) {
                return true;
            }
        }
        return false;
    }
}

