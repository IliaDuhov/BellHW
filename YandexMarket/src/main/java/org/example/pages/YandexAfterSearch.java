package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class YandexAfterSearch extends YandexMarketFirstPage {

    public YandexAfterSearch(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    public boolean checkCorrectPage(String subSection) {
        List<WebElement> header = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                " //h1[text()='" + subSection + "']"
        )));
        return !header.isEmpty();
    }

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
            WebElement allBrands = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//div[@data-auto='filter' and contains(., 'Бренд')]//div[@data-baobab-name='showMoreFilters']//button"
            )));
            allBrands.click();

            WebElement brandToFind = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                    "//fieldset//input[@placeholder='Найти']"
            )));

            for (String brand : brands) {
                if (!selectedBrands.contains(brand)) {
                    brandToFind.click();
                    brandToFind.sendKeys(brand);

                    WebElement brandCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                            "//div[contains(@data-zone-data, '"+brand+"')]//label[@role='checkbox']"
                    )));
                    brandCheckbox.click();
                }
            }

        }
    }


    public boolean checkNumberOfElements(Integer minCount){
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//a[contains(@href, '/card/') and @tabindex='-1']"
        )));

        return products.size()>=minCount;
    }

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

    public String getFirstProductAfterSelect(){
        List<WebElement> productsAfterSelect = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
                "//div[contains(@id, '/content/page')]//span[@tabindex='0']"
        )));
        return productsAfterSelect.get(0).getText();
    }

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

