package org.example.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.example.helpers.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс представляющий собой страницу после поиска
 * @author IliaDuhov
 */
public class YandexAfterSearchPage extends BasePage{

    /**
     * Метод, проверяющий корректность страницы после поиска
     * @author IliaDuhov
     * @param header название подраздела
     * @return YandexAfterSearchPage
     */
    public YandexAfterSearchPage checkIfPageCorrect(String header){
        SelenideElement headerName = $x("//h1[contains(text(), '"+ header +"')]")
                .shouldBe(Condition.visible);
        Assertions.assertTrue(header.contains(header),"excpected header: "+header);
        return this;
    }

    /**
     * Метод, устанавливающий бренд продукта. Если бренды есть в начальном меню, то они выбираются. Если брендов нет,
     * производится поиск по всем брендам
     * @author IliaDuhov
     * @param brands бренды
     * @return YandexAfterSearchPage
     */
    public YandexAfterSearchPage selectBrands(String...brands){
        for(String brand: brands){
            List<SelenideElement> foundBrands = $$x(
                    "//div[@data-auto='filter' and contains(., 'Бренд')]//span[contains(text(), '" + brand + "')]");

            if(!foundBrands.isEmpty()){
                foundBrands.get(0).click();
            }else{
                SelenideElement showAllBrandsButton = $x(
                        "//div[@data-auto='filter' and contains(., 'Бренд')]//div//button//span[@role]")
                        .shouldBe(Condition.visible);
                showAllBrandsButton.click();

                SelenideElement brandToFindInput = $x("//fieldset//input[@placeholder='Найти']")
                        .shouldBe(Condition.interactable);
                brandToFindInput.clear();
                brandToFindInput.sendKeys(brand);

                SelenideElement brandToSelect = $x("//fieldset//label[@role='checkbox']//span[contains(text(), '" + brand + "')]")
                        .shouldBe(Condition.interactable);
                brandToSelect.click();
            }
        }
        return this;
    }

    /**
     * Метод, проверяющий применились ли фильтры ко всем элементам
     * @author IliaDuhov
     * @param brands бренды
     * @return YandexAfterSearchPage
     */
    public YandexAfterSearchPage checkFilterApplied(String... brands) {
        int pageNumber = 1;
        Set<String> processedTitles = new HashSet<>();
        boolean hasMoreProducts = true;

        while (hasMoreProducts) {
            List<SelenideElement> products = $$x("//a[contains(@href, '/card/') and @tabindex]");
            List<SelenideElement> titles = $$x("//div[contains(@id, '/content/page')]//span[@tabindex]");

            int newItemsCount = 0;
            int minSize = Math.min(products.size(), titles.size());

            for (int i = 0; i < minSize; i++) {
                String titleText = titles.get(i).getText().toLowerCase();

                if (processedTitles.contains(titleText)) {
                    continue;
                }
                processedTitles.add(titleText);
                newItemsCount++;
                boolean brandMatch = Arrays.stream(brands)
                        .anyMatch(brand -> titleText.contains(brand.toLowerCase()));
                if(!brandMatch){
                    continue;
                }
                Assertions.assertTrue(brandMatch,
                        "Товар '" + titleText + "' не содержит ожидаемых брендов: " + Arrays.toString(brands));
            }

            if (!products.isEmpty()) {
                SelenideElement lastProduct = products.get(products.size() - 1);
                actions().moveToElement(lastProduct).perform();
            }

            List<SelenideElement> newProducts = $$x("//a[contains(@href, '/card/') and @tabindex]");
            List<SelenideElement> newTitles = $$x("//div[contains(@id, '/content/page')]//span[@tabindex]");

            int newMinSize = Math.min(newProducts.size(), newTitles.size());
            if (newMinSize <= processedTitles.size()) {
                hasMoreProducts = false;
            } else {
                pageNumber++;
            }
        }

        List<SelenideElement> allProducts = $$x("//a[contains(@href, '/card/') and @tabindex]");
        if (!allProducts.isEmpty()) {
            SelenideElement firstProduct = allProducts.get(0);
            actions().moveToElement(firstProduct).perform();
        }

        return this;
    }
}
