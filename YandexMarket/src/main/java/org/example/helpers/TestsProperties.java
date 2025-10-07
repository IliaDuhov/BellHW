package org.example.helpers;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для доступа к константным перменным в файле .properties
 * @author IliaDuhov
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
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
}
