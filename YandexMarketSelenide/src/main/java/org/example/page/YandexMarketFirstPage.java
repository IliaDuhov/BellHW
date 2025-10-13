package org.example.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс представляющий собой первую страницу
 * @author IliaDuhov
 */
public class YandexMarketFirstPage extends BasePage{


    /**
     * Метод открытия каталога
     * @author IliaDuhov
     * @return YandexMarketFirstPage
     */
    @Step("Открываем каталог")
    public YandexMarketFirstPage openCatalog(){
        SelenideElement catalogButton = $x("//button//span[text()='Каталог']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.interactable);
        catalogButton.click();
        return this;
    }

    /**
     * Метод наведения курсора на выбранный раздел
     * @author IliaDuhov
     * @param sectionName название раздела
     * @return YandexMarketFirstPage
     */
    @Step("Наводим курсор на раздел: {sectionName}")
    public YandexMarketFirstPage moveCursorToSection(String sectionName){
        SelenideElement section = $x("//ul[@role='tablist']/li/a/span[contains(text(),'" + sectionName + "')]")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.interactable);
        actions().moveToElement(section).perform();
        return this;
    }

    /**
     * Метод перехода к подразделу
     * @author IliaDuhov
     * @param subSectionName название подраздела
     * @param typeNextPage следующая страница после выполнения метода
     * @return YandexMarketFirstPage
     */
    @Step("Наводим курсор на подраздел: {subSectionName}")
    public <T extends BasePage> T moveToSubSection(String subSectionName, Class<T> typeNextPage){
        SelenideElement subSection = $x("//div[@aria-level]//a[text()='"+subSectionName+"']")
                .shouldBe(Condition.interactable);
        subSection.click();
        return typeNextPage.cast(page(typeNextPage));
    }
}
