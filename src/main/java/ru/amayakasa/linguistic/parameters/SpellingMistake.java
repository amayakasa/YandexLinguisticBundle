package ru.amayakasa.linguistic.parameters;

import java.text.MessageFormat;

/**
 * Список грамматических ошибок для Яндекс.Спеллера.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public enum SpellingMistake {
    NONE(0, "There is no spelling mistake in this word!"),
    ERROR_UNKNOWN_WORD(1, "The word is not in the dictionary"),
    ERROR_REPEAT_WORD(2, "The repetition of the word"),
    ERROR_CAPITALIZATION(3, "Incorrect use of uppercase and lowercase letters"),
    ERROR_TOO_MANY_ERRORS(4, "The text contains too many errors");

    public final int code;
    public final String description;


    SpellingMistake(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SpellingMistake byCode(int code) {
        for (SpellingMistake mistake : values()) if (mistake.code == code) return mistake;

        throw new IllegalArgumentException(MessageFormat.format("Unknown spelling mistake code: {0}", code));
    }
}
