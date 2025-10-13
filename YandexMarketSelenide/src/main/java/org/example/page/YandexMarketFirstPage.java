package org.example.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

public class YandexMarketFirstPage extends BasePage{

    public YandexMarketFirstPage openCatalog(){
        SelenideElement catalogButton = $x("//button//span[text()='Каталог']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.interactable);
        catalogButton.click();
        return this;
    }

    public YandexMarketFirstPage moveCursorToSection(String sectionName){
        SelenideElement section = $x("//ul[@role='tablist']/li/a/span[contains(text(),'" + sectionName + "')]")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.interactable);
        actions().moveToElement(section).perform();
        return this;
    }

    public YandexAfterSearchPage moveToSubSection(String subSectionName){
        SelenideElement subSection = $x("//div[@aria-level]//a[text()='"+subSectionName+"']")
                .shouldBe(Condition.interactable);
        subSection.click();
        return new YandexAfterSearchPage();
    }
}
