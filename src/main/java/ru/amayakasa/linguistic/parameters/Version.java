package ru.amayakasa.linguistic.parameters;

/**
 * Список версий API для Яндекс.Переводчика, Яндекс.Словаря, Яндекс.Предиктора и Яндекс.Спеллера.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexPredictor}   — Яндекс.Предиктор;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public enum Version {

    DICTIONARY_V1("v1"),
    PREDICTOR_V1("v1"),
    TRANSLATE_V1_5("v1.5"),
    DICTIONARY_LATEST("v1"),
    PREDICTOR_LATEST("v1"),
    SPELLER_LATEST("?"),
    TRANSLATE_LATEST("v1.5");

    private final String version;

    Version(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return version;
    }

}
