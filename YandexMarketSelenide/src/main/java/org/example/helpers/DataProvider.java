package org.example.helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * Класс DataProvider для параметризации теста
 * @author IliaDuhov
 */
public class DataProvider {

    /**
     * Метод для параметризации теста
     * @author IliaDuhov
     */
    public static Stream<Arguments> provideDataCheckingLaptops(){
        return Stream.of(
                Arguments.of("Электроника", "Мобильные телефоны", new String[]{"Samsung"}));
    }
}

