package ru.amayakasa.linguistic.http;

/**
 * Колбэк для методов Яндекс.Переводчика, Яндекс.Словаря, Яндекс.Предиктора и Яндекс.Спеллера.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexPredictor}   — Яндекс.Предиктор;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public interface YandexCallback<T> {

    /**
     * Действия в случае корректного ответа от вышеперечисленных API.
     *
     * @param response пропарсенный и обернутый ответ на запрос к API.
     */
    void onResponse(T response);

    /**
     * Действия в случае некорректного ответа от вышеперечисленных API,
     * или же в случае какой-либо другой ошибки во время запроса к API.
     *
     * @param throwable ошибка собственной персоной.
     */
    void onFailure(Throwable throwable);

}
