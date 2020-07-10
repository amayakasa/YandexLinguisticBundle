package ru.amayakasa.linguistic.response;

import ru.amayakasa.linguistic.parameters.SpellingMistake;

import java.util.List;

/**
 * Объект-враппер для представления ответов Яндекс.Спеллера в удобной форме.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public class Phrase {

    private String text;

    private List<Word> misspelledWords;

    /**
     * Конструктор для инициализации нового объекта #Phrase.
     *
     * @param text            исходная фраза, которая нуждалась в проверке;
     * @param misspelledWords список слов, в которых найдены ошибки.
     */
    public Phrase(String text, List<Word> misspelledWords) {
        this.text = text;
        this.misspelledWords = misspelledWords;
    }

    /**
     * Геттер для получения исходной фразы, которая нуждалась в проверке.
     *
     * @return исходный фраза, которая нуждалась в проверке.
     */
    public String getText() {
        return text;
    }

    /**
     * Геттер для получения списка слов, в которых найдены ошибки.
     *
     * @return список слов, в которых найдены ошибки.
     */
    public List<Word> getMisspelledWords() {
        return misspelledWords;
    }

    /**
     * Вложенный объект-враппер для представления ответов Яндекс.Спеллера в удобной форме.
     * Данный объект используется в {@link ru.amayakasa.linguistic.response.Phrase}.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
     *
     * @author Amayakasa
     */
    public static class Word {

        private SpellingMistake mistake;

        private int position, row, column, length;

        private String text;

        private List<String> variants;

        /**
         * Конструктор для инициализации нового объекта #Word.
         *
         * @param mistake  ошибка допущенная в слове;
         * @param position позиция слова, в котором найдена ошибка;
         * @param row      номер строки, в которой найдена ошибка;
         * @param column   номер столбца, в котором найдена ошибка;
         * @param length   длина слова, в котором найдена ошибка;
         * @param text     исходное слово, в котором были найдены ошибки;
         * @param variants список возможных значений.
         */
        public Word(SpellingMistake mistake, int position, int row, int column, int length, String text, List<String> variants) {
            this.mistake = mistake;
            this.position = position;
            this.row = row;
            this.column = column;
            this.length = length;
            this.text = text;
            this.variants = variants;
        }

        /**
         * Геттер для получения ошибки допущенной в слове.
         *
         * @return ошибка, допущенная в слове.
         */
        public SpellingMistake getMistake() {
            return mistake;
        }

        /**
         * Геттер для получения позиции слова, в котором найдена ошибка.
         *
         * @return позиция слова, в котором найдена ошибка.
         */
        public int getPosition() {
            return position;
        }

        /**
         * Геттер для получения номера строки, в которой найдена ошибка.
         *
         * @return номер строки, в которой найдена ошибка.
         */
        public int getRow() {
            return row;
        }

        /**
         * Геттер для получения номера столбца, в котором найдена ошибка.
         *
         * @return номер столбца, в котором найдена ошибка.
         */
        public int getColumn() {
            return column;
        }

        /**
         * Геттер для получения длины слова, в котором найдена ошибка.
         *
         * @return длину слова, в котором найдена ошибка.
         */
        public int getLength() {
            return length;
        }

        /**
         * Геттер для получения исходного слова, в котором были найдены ошибки.
         *
         * @return исходное слово, в котором были найдены ошибки.
         */
        public String getText() {
            return text;
        }

        /**
         * Геттер для получения списка возможных значений.
         *
         * @return список возможных значений.
         */
        public List<String> getVariants() {
            return variants;
        }
    }
}
