package ru.amayakasa.linguistic.parameters;


/**
 * Список возможных опций для настройки поиска Яндекс.Словаря.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь.
 *
 * @author Amayakasa
 */
public enum Flag {
    FAMILY(0x0001),
    SHORT_POS(0x0002),
    MORPHO(0x0004),
    POS_FILTER(0x0008);

    public final int bitmask;

    Flag(int bitmask) {
        this.bitmask = bitmask;
    }
}
