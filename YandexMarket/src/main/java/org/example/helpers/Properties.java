package org.example.helpers;

import org.aeonbits.owner.ConfigFactory;

/**
 * Класс для доступа к константным перменным в файле .properties
 * @author IliaDuhov
 */
public class Properties {

    /**
     * Поле для доступа к константным перменным в файле .properties
     * @author IliaDuhov
     */
    public static TestsProperties testsProperties = ConfigFactory.create(TestsProperties.class);
}
