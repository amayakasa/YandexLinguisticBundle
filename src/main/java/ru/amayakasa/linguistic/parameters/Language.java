package ru.amayakasa.linguistic.parameters;

import java.text.MessageFormat;

/**
 * Список всех возможных языков, используемых Яндекс.Переводчиком.
 * Часть используется Яндекс.Словарем, Яндекс.Предиктором и Яндекс.Спеллером.
 * Для Яндекс.Словаря были специально созданы языковые пары, смотрите ниже:
 * {@link ru.amayakasa.linguistic.parameters.LanguagePair}
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexPredictor}   — Яндекс.Предиктор;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public enum Language {
    AUTODETECT("", "Autodetect"),
    AZERBAIJAN("az", "Azerbaijan"),
    AFRIKAANS("af", "Afrikaans"),
    ALBANIAN("sq", "Albanian"),
    AMHARIC("am", "Amharic"),
    ARABIC("ar", "Arabic"),
    ARMENIAN("hy", "Armenian"),
    BASHKIR("ba", "Bashkir"),
    BASQUE("eu", "Basque"),
    BELARUSIAN("be", "Belarusian"),
    BENGALI("bn", "Bengali"),
    BOSNIAN("bs", "Bosnian"),
    BULGARIAN("bg", "Bulgarian"),
    BURMESE("my", "Burmese"),
    CATALAN("ca", "Catalan"),
    CEBUANO("ceb", "Cebuano"),
    CHINESE("zh", "Chinese"),
    CHUVASH("cv", "Chuvash"),
    CROATIAN("hr", "Croatian"),
    CZECH("cs", "Czech"),
    DANISH("da", "Danish"),
    DUTCH("nl", "Dutch"),
    ELVISH_SINDARIN("sjn", "Elvish (Sindarin)"),
    EMOJI("emj", "Emoji"),
    ENGLISH("en", "English"),
    ESPERANTO("eo", "Esperanto"),
    ESTONIAN("et", "Estonian"),
    FINNISH("fi", "Finnish"),
    FRENCH("fr", "French"),
    GALICIAN("gl", "Galician"),
    GEORGIAN("ka", "Georgian"),
    GERMAN("de", "German"),
    GREEK("el", "Greek"),
    GUJARATI("gu", "Gujarati"),
    HAITIAN("ht", "Haitian"),
    HEBREW("he", "Hebrew"),
    HILL_MARI("mrj", "Hill Mari"),
    HINDI("hi", "Hindi"),
    HUNGARIAN("hu", "Hungarian"),
    ICELANDIC("is", "Icelandic"),
    INDONESIAN("id", "Indonesian"),
    IRISH("ga", "Irish"),
    ITALIAN("it", "Italian"),
    JAPANESE("ja", "Japanese"),
    JAVANESE("jv", "Javanese"),
    KANNADA("kn", "Kannada"),
    KAZAKH("kk", "Kazakh"),
    KAZAKH_LATIN("kazlat", "Kazakh (Latin)"),
    KHMER("km", "Khmer"),
    KOREAN("ko", "Korean"),
    KYRGYZ("ky", "Kyrgyz"),
    LAOTIAN("lo", "Laotian"),
    LATIN("la", "Latin"),
    LATVIAN("lv", "Latvian"),
    LITHUANIAN("lt", "Lithuanian"),
    LUXEMBOURGISH("lb", "Luxembourgish"),
    MACEDONIAN("mk", "Macedonian"),
    MALAGASY("mg", "Malagasy"),
    MALAY("ms", "Malay"),
    MALAYALAM("ml", "Malayalam"),
    MALTESE("mt", "Maltese"),
    MAORI("mi", "Maori"),
    MARATHI("mr", "Marathi"),
    MARI("mhr", "Mari"),
    MONGOLIAN("mn", "Mongolian"),
    NEPALI("ne", "Nepali"),
    NORWEGIAN("no", "Norwegian"),
    PAPIAMENTO("pap", "Papiamento"),
    PERSIAN("fa", "Persian"),
    POLISH("pl", "Polish"),
    PORTUGUESE("pt", "Portuguese"),
    PUNJABI("pa", "Punjabi"),
    ROMANIAN("ro", "Romanian"),
    RUSSIAN("ru", "Russian"),
    SCOTTISH("gd", "Scottish"),
    SERBIAN("sr", "Serbian"),
    SINHALA("si", "Sinhala"),
    SLOVAKIAN("sk", "Slovakian"),
    SLOVENIAN("sl", "Slovenian"),
    SPANISH("es", "Spanish"),
    SUNDANESE("su", "Sundanese"),
    SWAHILI("sw", "Swahili"),
    SWEDISH("sv", "Swedish"),
    TAGALOG("tl", "Tagalog"),
    TAJIK("tg", "Tajik"),
    TAMIL("ta", "Tamil"),
    TATAR("tt", "Tatar"),
    TELUGU("te", "Telugu"),
    THAI("th", "Thai"),
    TURKISH("tr", "Turkish"),
    UDMURT("udm", "Udmurt"),
    UKRAINIAN("uk", "Ukrainian"),
    URDU("ur", "Urdu"),
    UZBEK("uz", "Uzbek"),
    UZBEK_CYRILLIC("uzbcyr", "Uzbek (Cyrillic)"),
    VIETNAMESE("vi", "Vietnamese"),
    WELSH("cy", "Welsh"),
    XHOSA("xh", "Xhosa"),
    YIDDISH("yi", "Yiddish"),
    YAKUT("sah", "Yakut");

    public final String code;
    public final String description;

    Language(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Language byCode(String code) {
        for (Language language : values()) if (language.code.equals(code)) return language;

        throw new IllegalArgumentException(MessageFormat.format("Unknown language code: {0}", code));
    }

    @Override
    public String toString() {
        return code;
    }
}
