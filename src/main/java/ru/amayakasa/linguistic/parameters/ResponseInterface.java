package ru.amayakasa.linguistic.parameters;

/**
 * Список интерфейса ответов для Яндекс.Переводчика, Яндекс.Словаря, Яндекс.Предиктора и Яндекс.Спеллера.
 * Здесь представлены как JSON, так и XML интерфейсы. На данный момент реализация есть только для JSON.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexPredictor}   — Яндекс.Предиктор;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public enum ResponseInterface {

    // =========== JSON интерфейсы ================
    DICTIONARY_JSON("dicservice.json"),
    PREDICTOR_JSON("predict.json"),
    SPELLER_JSON("spellservice.json"),
    TRANSLATE_JSON("tr.json"),
    // =========== XML  интерфейсы ================
    DICTIONARY_XML("dicservice"),
    PREDICTOR_XML("predict"),
    SPELLER_XML("spellservice"),
    TRANSLATE_XML("tr");

    private final String responseInterface;

    ResponseInterface(String responseInterface) {
        this.responseInterface = responseInterface;
    }

    @Override
    public String toString() {
        return responseInterface;
    }
}
