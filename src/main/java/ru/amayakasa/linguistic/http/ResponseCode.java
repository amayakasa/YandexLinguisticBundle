package ru.amayakasa.linguistic.http;

import ru.amayakasa.linguistic.YandexDictionary;
import ru.amayakasa.linguistic.YandexPredictor;
import ru.amayakasa.linguistic.YandexSpeller;
import ru.amayakasa.linguistic.YandexTranslator;

/**
 * Список кодов ответа от Яндекс.Переводчика, Яндекс.Словаря, Яндекс.Предиктора и Яндекс.Спеллера.
 * См. подробнее {@link YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link YandexDictionary}  — Яндекс.Словарь;
 * См. подробнее {@link YandexPredictor}   — Яндекс.Предиктор;
 * См. подробнее {@link YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public enum ResponseCode {

    OK(200, "The operation was completed successfully"),
    KEY_INVALID(401, "Invalid key for API"),
    KEY_BLOCKED(402, "Blocked key for API"),
    DAILY_REQUEST_LIMIT_EXCEEDED(403, "Exceeded the daily limit on the amount of requests"),
    DAILY_TEXT_LIMIT_EXCEEDED(404, "Exceeded the daily limit on the amount of text"),
    CHARACTER_LIMIT_EXCEEDED(413, "Exceeded the maximum allowed text size"),
    FAILED_TO_TRANSLATE(422, "The text cannot be translated"),
    LANGUAGE_NOT_SUPPORTED(501, "The specified language direction is not supported");

    public final int code;
    public final String description;


    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ResponseCode byCode(int code) {
        for (ResponseCode responseCode : values()) if (responseCode.code == code) return responseCode;

        return null;
    }
}
