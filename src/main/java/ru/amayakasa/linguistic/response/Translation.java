package ru.amayakasa.linguistic.response;

import ru.amayakasa.linguistic.parameters.Language;

/**
 * Объект-враппер для представления ответов Яндекс.Переводчика в удобной форме.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик.
 *
 * @author Amayakasa
 */
public class Translation {

    private String text;

    private Language from;
    private Language to;

    private String translation;

    /**
     * Конструктор для инициализации нового объекта #Translation.
     *
     * @param text        исходный текст перевода;
     * @param from        язык, с которого был совершен перевод;
     * @param to          язык, на который был совершен перевод;
     * @param translation перевод исходного текста.
     */
    public Translation(String text, Language from, Language to, String translation) {
        this.text = text;
        this.from = from;
        this.to = to;
        this.translation = translation;
    }

    /**
     * Геттер для получения исходного текста перевода.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator#getTextTranslation}
     *
     * @return исходный текст перевода.
     */
    public String getText() {
        return text;
    }

    /**
     * Геттер для получения языка, с которого был совершен перевод.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator#getTextTranslation}
     *
     * @return язык, с которого был совершен перевод.
     */
    public Language getFrom() {
        return from;
    }

    /**
     * Геттер для получения языка, на который был совершен перевод.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator#getTextTranslation}
     *
     * @return язык, на который был совершен перевод.
     */
    public Language getTo() {
        return to;
    }

    /**
     * Геттер для получения перевода исходного текста.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator#getTextTranslation}
     *
     * @return перевод исходного текста.
     */
    public String getTranslation() {
        return translation;
    }
}
