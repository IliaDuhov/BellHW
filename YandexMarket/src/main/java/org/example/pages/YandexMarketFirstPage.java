package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class YandexMarketFirstPage {

    protected WebDriver chromeDriver;
    protected WebDriverWait wait;
    protected Actions actions;



    public YandexMarketFirstPage(WebDriver chromeDriver){
        this.chromeDriver = chromeDriver;
        this.wait = new WebDriverWait(chromeDriver, 10);
        this.actions = new Actions(chromeDriver);
    }

    public void openCatalog(){
        WebElement catalogButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//button//span[text()='Каталог']")));
        catalogButton.click();
    }

    public void moveCursorOnSection(String sectionName){
        WebElement section = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//ul[@role='tablist']/li/a/span[contains(text(),'" + sectionName + "')]")));

        actions.moveToElement(section).perform();
    }

    public void moveToSubSection(String subSectionName){
        WebElement subSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@aria-level='2']//a[text()='"+subSectionName+"']"
        )));
        subSection.click();
    }

}
