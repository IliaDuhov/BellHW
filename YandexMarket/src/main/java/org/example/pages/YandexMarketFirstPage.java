package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.example.helpers.Properties.testsProperties;


/**
 * Класс представляющий собой первую страницу
 * @author IliaDuhov
 */
public class YandexMarketFirstPage {

    /**
     * Поле для инициализации драйвера
     * @author IliaDuhov
     */
    protected WebDriver chromeDriver;

    /**
     * Поле для инициализации ожиданий
     * @author IliaDuhov
     */
    protected WebDriverWait wait;

    /**
     * Поле типа Actions для работы с наведением курсора
     * @author IliaDuhov
     */
    protected Actions actions;

    /**
     * Конструктор инициализирующий поля класса YandexMarketFirstPage.java
     * @author IliaDuhov
     * @param chromeDriver драйвер
     */
    public YandexMarketFirstPage(WebDriver chromeDriver){
        this.chromeDriver = chromeDriver;
        this.wait = new WebDriverWait(chromeDriver, testsProperties.defaultTimeout());
        this.actions = new Actions(chromeDriver);
    }

    /**
     * Метод открытия каталога
     * @author IliaDuhov
     */
    public void openCatalog(){
        WebElement catalogButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//button//span[text()='Каталог']")));
        catalogButton.click();
    }

    /**
     * Метод наведения курсора на выбранный раздел
     * @author IliaDuhov
     * @param sectionName название раздела
     */
    public void moveCursorOnSection(String sectionName){
        WebElement section = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//ul[@role='tablist']/li/a/span[contains(text(),'" + sectionName + "')]")));

        actions.moveToElement(section).perform();
    }

    /**
     * Метод перехода к подразделу
     * @author IliaDuhov
     * @param subSectionName название подраздела
     */
    public void moveToSubSection(String subSectionName){
        WebElement subSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@aria-level='2']//a[text()='"+subSectionName+"']"
        )));
        subSection.click();
    }

}
