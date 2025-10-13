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

public class YandexAfterSearchPage extends BasePage{

    public YandexAfterSearchPage checkIfPageCorrect(String header){
        SelenideElement headerName = $x("//h1[contains(text(), '"+ header +"')]")
                .shouldBe(Condition.visible);
        Assertions.assertTrue(header.contains(header),"excpected header: "+header);
        return this;
    }

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

    public YandexAfterSearchPage checkFilterApplied(String...brands) {
        int pageNumber = 1;
        Set<String> processedTitles = new HashSet<>();
        boolean hasMoreProducts = true;

        while (hasMoreProducts) {
            List<SelenideElement> products = $$x("//a[contains(@href, '/card/') and @tabindex]");

            int newItemsCount = 0;

            for (int i = 0; i < products.size(); i++) {
                List<SelenideElement> titles = $$x("//div[contains(@id, '/content/page')]//span[@tabindex]");

                if (i >= titles.size()) break;

                String titleText = titles.get(i).getText().toLowerCase();

                if (processedTitles.contains(titleText)) continue;
                processedTitles.add(titleText);
                newItemsCount++;

                boolean brandMatch = Arrays.stream(brands)
                        .anyMatch(brand -> titleText.contains(brand.toLowerCase()));

                if (!brandMatch) {
                    continue;
                }
            }

            List<SelenideElement> refreshedProducts = $$x("//a[contains(@href, '/card/') and @tabindex]");

            if (!refreshedProducts.isEmpty()) {
                SelenideElement lastProduct = refreshedProducts.get(refreshedProducts.size() - 1);
                actions().moveToElement(lastProduct).perform();
            }

            if (newItemsCount == 0) {
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
