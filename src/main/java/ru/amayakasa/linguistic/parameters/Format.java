package ru.amayakasa.linguistic.parameters;

/**
 * Список возможных форматов текста для Яндекс.Переводчика и Яндекс.Спеллера.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public enum Format {

    PLAIN("plain"),
    HTML("html");

    public final String format;

    Format(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return format;
    }
}
