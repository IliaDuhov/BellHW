package org.example.helpers;

import io.qameta.allure.Step;

/**
 * Класс, переопределяющий assertTrue для отображение в allure отчёте
 * @author IliaDuhov
 */
public class Assertions {

    /**
     * Метод переопределяющий assertTrue для отображение в allure отчёте
     * @author IliaDuhov
     * @param condition состояние после проверки
     * @param message сообщение после проверки
     */
    @Step("Проверяем что нет ошибки: '{message}'")
    public static void assertTrue(boolean condition, String message){
        org.junit.jupiter.api.Assertions.assertTrue(condition, message);
    }
}
