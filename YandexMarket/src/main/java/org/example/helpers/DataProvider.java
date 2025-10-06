package org.example.helpers;



import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProvider {

    public static Stream<Arguments> provideDataCheckingLaptops(){
        return Stream.of(
                Arguments.of("Электроника", "Ноутбуки", 10000.0, 20000.0, "HP", "Lenovo", 5));
    }
}

