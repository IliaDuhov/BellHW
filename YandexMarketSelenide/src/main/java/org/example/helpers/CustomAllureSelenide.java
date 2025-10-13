package org.example.helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.SelenideLog;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import io.qameta.allure.util.ResultsUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Кастомная реализация Allure Selenide listener для расширенного логирования тестов
 * @author IliaDuhov
 */
public class CustomAllureSelenide extends AllureSelenide {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllureSelenide.class);

    /**
     * Флаг для сохранения скриншотов при выполнении шагов
     * @author IliaDuhov
     */
    private boolean saveScreenshots;

    /**
     * Флаг для сохранения HTML кода страницы
     * @author IliaDuhov
     */
    private boolean savePageHtml;

    /**
     * Флаг для включения шагов с локаторами Selenide в отчет
     * @author IliaDuhov
     */
    private boolean includeSelenideLocatorsSteps;

    /**
     * Коллекция Map типов логов браузера и их уровней для сохранения в отчет
     * @author IliaDuhov
     */
    private final Map<LogType, Level> logTypesToSave;

    /**
     * Поле жизненного цикла Allure для управления шагами и вложениями
     * @author IliaDuhov
     */
    private final AllureLifecycle lifecycle;

    /**
     * Конструктор инициализации по умолчанию
     * @author IliaDuhov
     */
    public CustomAllureSelenide() {
        this(Allure.getLifecycle());
    }

    /**
     * Конструктор с кастомным lifecycle Allure
     * @author IliaDuhov
     * @param lifecycle жизненный цикл Allure
     *
     */
    public CustomAllureSelenide(AllureLifecycle lifecycle) {
        this.saveScreenshots = true;
        this.savePageHtml = true;
        this.includeSelenideLocatorsSteps = true;
        this.logTypesToSave = new HashMap();
        this.lifecycle = lifecycle;
    }

    /**
     * Получает скриншот текущего состояния браузера в виде массива байт
     * @author IliaDuhov
     * @return Optional<byte[]>
     */
    private static Optional<byte[]> getScreenshotBytes() {
        try {
            return WebDriverRunner.hasWebDriverStarted() ?
                    Optional.of(((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES)) :
                    Optional.empty();
        } catch (WebDriverException var1) {
            LOGGER.warn("Could not get screen shot", var1);
            return Optional.empty();
        }
    }

    /**
     * Получает исходный код HTML страницы в виде массива байт
     * @author IliaDuhov
     * @return Optional<byte[]>
     *
     */
    private static Optional<byte[]> getPageSourceBytes() {
        try {
            return WebDriverRunner.hasWebDriverStarted() ?
                    Optional.of(WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)) :
                    Optional.empty();
        } catch (WebDriverException var1) {
            LOGGER.warn("Could not get page source", var1);
            return Optional.empty();
        }
    }

    /**
     * Получает логи браузера указанного типа и уровня
     * @author IliaDuhov
     * @param logType тип логов для получения
     * @param level уровень логирования
     * @return строку с объединенными логами браузера
     *
     */
    private static String getBrowserLogs(LogType logType, Level level) {
        return String.join("\n\n", Selenide.getWebDriverLogs(logType.toString(), level));
    }

    /**
     * Обрабатывает событие после выполнения шага теста
     * Сохраняет скриншоты, HTML страницы и логи в зависимости от настроек
     * @author IliaDuhov
     * @param event событие логирования
     *
     */
    @Override
    public void afterEvent(LogEvent event) {
        this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
            if (this.saveScreenshots) {
                getScreenshotBytes().ifPresent((bytes) -> {
                    this.lifecycle.addAttachment("Screenshot", "image/png", "png", bytes);
                });
            }

            if (this.savePageHtml) {
                getPageSourceBytes().ifPresent((bytes) -> {
                    this.lifecycle.addAttachment("Page source", "text/html", "html", bytes);
                });
            }

            if (!this.logTypesToSave.isEmpty()) {
                this.logTypesToSave.forEach((logType, level) -> {
                    byte[] content = getBrowserLogs(logType, level).getBytes(StandardCharsets.UTF_8);
                    this.lifecycle.addAttachment("Logs from: " + logType, "application/json", ".txt", content);
                });
            }
        });

        if (this.stepsShouldBeLogged(event)) {
            this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
                switch(event.getStatus()) {
                    case PASS:
                        this.lifecycle.updateStep((step) -> {
                            step.setStatus(Status.PASSED);
                        });
                        break;
                    case FAIL:
                        this.lifecycle.updateStep((stepResult) -> {
                            stepResult.setStatus((Status) ResultsUtils.getStatus(event.getError()).orElse(Status.BROKEN));
                            stepResult.setStatusDetails((StatusDetails)ResultsUtils.getStatusDetails(event.getError()).orElse(new StatusDetails()));
                        });
                        break;
                    default:
                        LOGGER.warn("Step finished with unsupported status {}", event.getStatus());
                }

                this.lifecycle.stopStep();
            });
        }
    }

    /**
     * Определяет, должны ли шаги быть залогированы в отчет
     * @author IliaDuhov
     * @param event событие логирования
     * @return boolean если шаг должен быть залогирован, false в противном случае
     *
     */
    private boolean stepsShouldBeLogged(LogEvent event) {
        return this.includeSelenideLocatorsSteps || !(event instanceof SelenideLog);
    }
}
