package ru.amayakasa.linguistic.parameters;

import java.text.MessageFormat;

/**
 * Список всех возможных языков пар, используемых Яндекс.Словарем.
 * Языковая пара является составным объектом из объекта языка, смотрите ниже:
 * {@link ru.amayakasa.linguistic.parameters.Language}
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь.
 *
 * @author Amayakasa
 */
public enum LanguagePair {

    RUSSIAN_RUSSIAN("ru-ru", Language.RUSSIAN, Language.RUSSIAN, "Russian-Russian"),
    RUSSIAN_ENGLISH("ru-en", Language.RUSSIAN, Language.ENGLISH, "Russian-English"),
    RUSSIAN_POLISH("ru-pl", Language.RUSSIAN, Language.POLISH, "Russian-Polish"),
    RUSSIAN_UKRAINIAN("ru-uk", Language.RUSSIAN, Language.UKRAINIAN, "Russian-Ukrainian"),
    RUSSIAN_GERMAN("ru-de", Language.RUSSIAN, Language.GERMAN, "Russian-German"),
    RUSSIAN_FRENCH("ru-fr", Language.RUSSIAN, Language.FRENCH, "Russian-French"),
    RUSSIAN_SPANISH("ru-es", Language.RUSSIAN, Language.SPANISH, "Russian-Spanish"),
    RUSSIAN_ITALIAN("ru-it", Language.RUSSIAN, Language.ITALIAN, "Russian-Italian"),
    RUSSIAN_TURKISH("ru-tr", Language.RUSSIAN, Language.TURKISH, "Russian-Turkish"),
    ENGLISH_RUSSIAN("en-ru", Language.ENGLISH, Language.RUSSIAN, "English-Russian"),
    ENGLISH_ENGLISH("en-en", Language.ENGLISH, Language.ENGLISH, "English-English"),
    ENGLISH_GERMAN("en-de", Language.ENGLISH, Language.GERMAN, "English-German"),
    ENGLISH_FRENCH("en-fr", Language.ENGLISH, Language.FRENCH, "English-French"),
    ENGLISH_SPANISH("en-es", Language.ENGLISH, Language.SPANISH, "English-Spanish"),
    ENGLISH_ITALIAN("en-it", Language.ENGLISH, Language.ITALIAN, "English-Italian"),
    ENGLISH_TURKISH("en-tr", Language.ENGLISH, Language.TURKISH, "English-Turkish"),
    POLISH_RUSSIAN("pl-ru", Language.POLISH, Language.RUSSIAN, "Polish-Russian"),
    UKRAINIAN_RUSSIAN("uk-ru", Language.UKRAINIAN, Language.RUSSIAN, "Ukrainian-Russian"),
    GERMAN_RUSSIAN("de-ru", Language.GERMAN, Language.RUSSIAN, "German-Russian"),
    GERMAN_ENGLISH("de-en", Language.GERMAN, Language.ENGLISH, "German-English"),
    FRENCH_RUSSIAN("fr-ru", Language.FRENCH, Language.RUSSIAN, "French-Russian"),
    FRENCH_ENGLISH("fr-en", Language.FRENCH, Language.ENGLISH, "French-English"),
    SPANISH_RUSSIAN("es-ru", Language.SPANISH, Language.RUSSIAN, "Spanish-Russian"),
    SPANISH_ENGLISH("es-en", Language.SPANISH, Language.ENGLISH, "Spanish-English"),
    ITALIAN_RUSSIAN("it-ru", Language.ITALIAN, Language.RUSSIAN, "Italian-Russian"),
    ITALIAN_ENGLISH("it-en", Language.ITALIAN, Language.ENGLISH, "Italian-English"),
    TURKISH_RUSSIAN("tr-ru", Language.TURKISH, Language.RUSSIAN, "Turkish-Russian"),
    TURKISH_ENGLISH("tr-en", Language.TURKISH, Language.ENGLISH, "Turkish-English");

    public final String code;
    public final Language from;
    public final Language to;
    public final String description;

    LanguagePair(String code, Language from, Language to, String description) {
        this.code = code;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public static LanguagePair byCode(String code) {
        for (LanguagePair languagePair : values()) if (languagePair.code.equals(code)) return languagePair;

        throw new IllegalArgumentException(MessageFormat.format("Unknown language pair code: {0}", code));
    }

    public static LanguagePair byLanguages(Language from, Language to) {
        for (LanguagePair languagePair : values()) {
            if (languagePair.from.equals(from) && languagePair.to.equals(to)) return languagePair;
        }

        throw new IllegalArgumentException(MessageFormat.format("Unknown language pair code: {0}-{1}", from, to));
    }

    @Override
    public String toString() {
        return code;
    }
}
