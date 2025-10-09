package org.example.helpers;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для доступа к константным перменным в файле .properties
 * @author IliaDuhov
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "file:src/main/resources/tests.properties"
})
public interface TestsProperties extends Config {

    /**
     * Возвращает адрес сайта yandexmarket.url
     * @return String
     */
    @Config.Key("yandexmarket.url")
    String yandexMarketUrl();

    /**
     * Возвращает время для timeout
     * @return int
     */
    @Config.Key("default.timeout")
    int defaultTimeout();

    /**
     * Возвращает название переменной окружения драйвера
     * @return int
     */
    @Config.Key("chrome.driver.path")
    String chromeDriverPath();
}
