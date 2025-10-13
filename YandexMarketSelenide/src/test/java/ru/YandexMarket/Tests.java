package ru.YandexMarket;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.example.helpers.Properties.testsProperties;

public class Tests extends BaseTests{

    @Test
    public void firstTest(){
        open(testsProperties.yandexMarketUrl());
    }
}
