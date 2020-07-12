package ru.amayakasa.linguistic.parameters;

/**
 * Список всех опций для настройки анализа Яндекс.Спеллера и параметров Яндекс.Переводчика.
 * Примечание. Опция "INCLUDE_AUTODETECT" используется сугубо для Яндекс.Переводчика.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public enum Option {

    NONE(0),
    INCLUDE_AUTODETECT(1), // опция для Яндекс.Переводчика
    IGNORE_DIGITS(2),
    IGNORE_URLS(4),
    FIND_REPEAT_WORDS(8),
    IGNORE_CAPITALIZATION(512);

    public final int option;

    Option(int option) {
        this.option = option;
    }
}
