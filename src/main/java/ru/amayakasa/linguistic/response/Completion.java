package ru.amayakasa.linguistic.response;

import java.util.List;

/**
 * Объект-враппер для представления ответов Яндекс.Предиктора в удобной форме.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexPredictor}   — Яндекс.Предиктор.
 *
 * @author Amayakasa
 */
public class Completion {

    private String text;

    private List<String> variants;

    private int position;

    private boolean wordEnded;

    /**
     * Конструктор для инициализации нового объекта #Completion.
     *
     * @param text      исходный текст, который нуждался в дополнении;
     * @param variants  список вариантов завершения слова;
     * @param position  позиция в слове, которое нуждалось в завершении;
     * @param wordEnded признак конца указанного слова.
     */
    public Completion(String text, List<String> variants, int position, boolean wordEnded) {
        this.text = text;
        this.variants = variants;
        this.position = position;
        this.wordEnded = wordEnded;
    }

    /**
     * Геттер для получения исходного текста, который нуждался в дополнении.
     *
     * @return исходный текст, который нуждался в дополнении.
     */
    public String getText() {
        return text;
    }

    /**
     * Геттер для получения списка вариантов завершения слова.
     *
     * @return список вариантов завершения слова.
     */
    public List<String> getVariants() {
        return variants;
    }

    /**
     * Геттер для получения позиции в слове, которое нуждалось в завершении.
     *
     * @return позиция в слове, которое нуждалось в заверешении.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Геттер для получения признака конца указанного слова.
     *
     * @return признак конца указанного слова.
     */
    public boolean isWordEnded() {
        return wordEnded;
    }
}
