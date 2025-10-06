package org.example.helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "file:src/main/resources/tests.properties"
})
public interface TestsProperties extends Config {

    @Config.Key("yandexmarket.url")
    String yandexMarketUrl();

    @Config.Key("default.timeout")
    int defaultTimeout();
}
