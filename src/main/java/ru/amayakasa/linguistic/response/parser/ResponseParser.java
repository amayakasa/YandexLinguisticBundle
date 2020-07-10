package ru.amayakasa.linguistic.response.parser;

import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Response;
import ru.amayakasa.linguistic.parameters.LanguagePair;
import ru.amayakasa.linguistic.response.Translation;
import ru.amayakasa.linguistic.response.Completion;
import ru.amayakasa.linguistic.response.Dictionary;
import ru.amayakasa.linguistic.response.Phrase;
import ru.amayakasa.linguistic.parameters.Language;
import ru.amayakasa.linguistic.parameters.SpellingMistake;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ResponseParser {

    // =========================== УФ, ГОТОВЧЕНКО ===========================
    // TODO: Оптимизировать парсинг

    public static Dictionary parseDictionary(Response<ResponseBody> response) throws IOException {
        JSONObject object = new JSONObject(response.body().string());

        List<Dictionary.Definition> definitions = new ArrayList<>();

        if (object.has("def")) definitions = parseDefinitions(object.getJSONArray("def"));

        return new Dictionary(definitions);
    }

    public static List<LanguagePair> parseDictionaryDirections(Response<ResponseBody> response) throws IOException {
        JSONArray array = new JSONArray(response.body().string());

        List<LanguagePair> dictionaryDirections = new ArrayList<>();

        int iterator = 0;
        while (iterator < array.length()) {
            dictionaryDirections.add(LanguagePair.byCode(array.getString(iterator)));
            iterator++;
        }
        return dictionaryDirections;
    }

    /**
     * Парсинг и обертка ответа от Яндекс.Предиктора.
     *
     * @param response ответ на запрос, пришедший от вышеупомянотого сервиса;
     * @return список объектов #Language для удобного взаимодействия;
     * @throws IOException в случае, если нельзя пропарсить JSON-объект.
     */
    public static List<Language> parsePredictionDirections(Response<ResponseBody> response) throws IOException {
        JSONArray array = new JSONArray(response.body().string());

        List<Language> predictionDirections = new ArrayList<>();

        int iterator = 0;
        while (iterator < array.length()) {
            predictionDirections.add(Language.byCode(array.getString(iterator)));
            iterator++;
        }
        return predictionDirections;
    }

    /**
     * Парсинг и обертка ответа от Яндекс.Предиктора.
     *
     * @param text     исходный текст, который нуждался в дополнении;
     * @param response ответ на запрос, пришедший от вышеупомянотого сервиса;
     * @return объект-враппер #Completion для удобного взаимодействия;
     * @throws IOException в случае, если нельзя пропарсить JSON-объект.
     */
    public static Completion parseCompletion(String text, Response<ResponseBody> response) throws IOException {
        JSONObject object = new JSONObject(response.body().string());

        List<String> variants = new ArrayList<>();
        boolean ended = object.getBoolean("endOfWord");
        int position = object.getInt("pos");

        JSONArray array = object.getJSONArray("text");

        int iterator = 0;
        while (iterator < array.length()) {
            variants.add(array.getString(iterator));
            iterator++;
        }

        return new Completion(text, variants, position, ended);
    }

    /**
     * Парсинг и обертка ответа от Яндекс.Спеллера.
     *
     * @param text     исходная фраза, которая нуждалась в проверке;
     * @param response ответ на запрос, пришедший от вышеупомянотого сервиса;
     * @return объект-враппер #Phrase для удобного взаимодействия;
     * @throws IOException в случае, если нельзя пропарсить JSON-объект.
     */
    public static Phrase parsePhrase(String text, Response<ResponseBody> response) throws IOException {
        JSONArray array = new JSONArray(response.body().string());

        return parsePhrase(text, array);
    }

    /**
     * Парсинг и обертка ответа от Яндекс.Спеллера.
     *
     * @param text     исходные фразы, которая нуждались в проверке;
     * @param response ответ на запрос, пришедший от вышеупомянотого сервиса;
     * @return список объектов-врапперов #Phrase для удобного взаимодействия;
     * @throws IOException в случае, если нельзя пропарсить JSON-объект.
     */
    public static List<Phrase> parsePhrases(String[] text, Response<ResponseBody> response) throws IOException {
        JSONArray array = new JSONArray(response.body().string());

        List<Phrase> phrases = new ArrayList<>();

        int iterator = 0;
        while (iterator < array.length()) {
            Phrase phrase = ResponseParser.parsePhrase(text[iterator], array.getJSONArray(iterator));
            phrases.add(phrase);
            iterator++;
        }

        return phrases;
    }

    /**
     * Парсинг и обертка ответа от Яндекс.Переводчика.
     *
     * @param response ответ на запрос, пришедший от вышеупомянотого сервиса;
     * @return список объектов #Language для удобного взаимодействия;
     * @throws IOException в случае, если нельзя пропарсить JSON-объект.
     */
    public static List<Language> parseTranslationDirections(Response<ResponseBody> response) throws IOException {
        JSONObject object = new JSONObject(response.body().string());

        List<Language> translationDirections = new ArrayList<>();

        for (String key : object.getJSONObject("langs").keySet()) translationDirections.add(Language.byCode(key));

        return translationDirections;
    }

    /**
     * Парсинг и обертка ответа от Яндекс.Переводчика.
     *
     * @param response ответ на запрос, пришедший от вышеупомянотого сервиса;
     * @return объект #Language для удобного взаимодействия;
     * @throws IOException в случае, если нельзя пропарсить JSON-объект.
     */
    public static Language parseTextLanguage(Response<ResponseBody> response) throws IOException {
        JSONObject object = new JSONObject(response.body().string());

        return Language.byCode(object.getString("lang"));
    }

    /**
     * Парсинг и обертка ответа от Яндекс.Переводчика.
     *
     * @param text     исходный текста перевода;
     * @param response ответ на запрос, пришедший от вышеупомянотого сервиса;
     * @return объект-враппер #Translation для удобного взаимодействия;
     * @throws IOException в случае, если нельзя пропарсить JSON-объект.
     */
    public static Translation parseTextTranslation(String text, Response<ResponseBody> response) throws IOException {
        JSONObject object = new JSONObject(response.body().string());

        Language from, to;

        String[] values = object.getString("lang").split("-");

        if (values.length == 2) {
            from = Language.byCode(values[0]);
            to = Language.byCode(values[1]);
        } else {
            from = Language.AUTODETECT;
            to = Language.byCode(values[0]);
        }

        String translation = object.getJSONArray("text").getString(0);

        return new Translation(text, from, to, translation);
    }


    // =========================== ВНУТРЯНКА, ХЫ  ===========================
    // TODO: Сделать документацию, оптимизировать парсинг

    /**
     * Внутреняя функция для парсинга и обертки ответа от Яндекс.Спеллера.
     *
     * @param text  исходная фраза, которая нуждалась в проверке;
     * @param array JSON-массив для дальнейшего парсинга;
     * @return объект-враппер #Phrase для удобного взаимодействия.
     */
    private static Phrase parsePhrase(String text, JSONArray array) {
        List<Phrase.Word> misspelledWords = new ArrayList<>();

        int iterator = 0;
        while (iterator < array.length()) {
            JSONObject object = array.getJSONObject(iterator);

            SpellingMistake mistake = SpellingMistake.byCode(object.getInt("code"));
            int position = object.getInt("pos");
            int row = object.getInt("row");
            int column = object.getInt("col");
            int length = object.getInt("len");
            String word = object.getString("word");
            List<String> variants = new ArrayList<>();

            JSONArray s = object.getJSONArray("s");

            int counter = 0;
            while (counter < s.length()) {
                variants.add(s.getString(counter));
                counter++;
            }

            Phrase.Word misspelledWord = new Phrase.Word(mistake, position, row, column, length, word, variants);

            misspelledWords.add(misspelledWord);
            iterator++;
        }
        return new Phrase(text, misspelledWords);
    }

    private static List<Dictionary.Definition> parseDefinitions(JSONArray array) {
        List<Dictionary.Definition> definitions = new ArrayList<>();

        int iterator = 0;

        while (iterator < array.length()) {
            Dictionary.Definition definition = parseDefinition(array.getJSONObject(iterator));

            if (definition != null) definitions.add(definition);

            iterator++;
        }

        return definitions;
    }

    private static Dictionary.Definition parseDefinition(JSONObject object) {
        String text = null;
        List<Dictionary.Translation> translations = new ArrayList<>();

        if (object.has("text")) text = object.getString("text");

        if (object.has("tr")) translations = parseTranslations(object.getJSONArray("tr"));

        Dictionary.Definition definition = new Dictionary.Definition(text, translations);

        if (object.has("ts")) definition.setTranscription(object.getString("ts"));

        if (object.has("pos")) definition.setPartOfSpeech(object.getString("pos"));

        if (object.has("gen")) definition.setGender(object.getString("gen"));

        if (object.has("num")) definition.setNumber(object.getString("num"));

        return definition.getText() != null ? definition : null;
    }

    private static List<Dictionary.Translation> parseTranslations(JSONArray array) {
        List<Dictionary.Translation> translations = new ArrayList<>();

        int iterator = 0;

        while (iterator < array.length()) {
            Dictionary.Translation translation = parseTranslation(array.getJSONObject(iterator));

            if (translation != null) translations.add(translation);

            iterator++;
        }

        return translations;
    }

    private static Dictionary.Translation parseTranslation(JSONObject object) {
        String text = null;
        List<Dictionary.Example> examples = new ArrayList<>();
        List<Dictionary.Meaning> meanings = new ArrayList<>();
        List<Dictionary.Synonym> synonyms = new ArrayList<>();

        if (object.has("text")) text = object.getString("text");

        if (object.has("syn")) synonyms = parseSynonyms(object.getJSONArray("syn"));

        if (object.has("mean")) meanings = parseMeanings(object.getJSONArray("mean"));

        if (object.has("ex")) examples = parseExamples(object.getJSONArray("ex"));

        Dictionary.Translation translation = new Dictionary.Translation(text, synonyms, meanings, examples);

        if (object.has("pos")) translation.setPartOfSpeech(object.getString("pos"));

        if (object.has("gen")) translation.setGender(object.getString("gen"));

        if (object.has("num")) translation.setNumber(object.getString("num"));

        return translation.getText() != null ? translation : null;
    }

    private static List<Dictionary.Example> parseExamples(JSONArray array) {
        List<Dictionary.Example> examples = new ArrayList<>();

        int iterator = 0;

        while (iterator < array.length()) {
            Dictionary.Example example = parseExample(array.getJSONObject(iterator));

            if (example != null) examples.add(example);

            iterator++;
        }

        return examples;
    }

    private static Dictionary.Example parseExample(JSONObject object) {
        String text = null;
        List<Dictionary.Snippet> snippets = new ArrayList<>();

        if (object.has("text")) text = object.getString("text");

        if (object.has("tr")) snippets = parseSnippets(object.getJSONArray("tr"));

        Dictionary.Example example = new Dictionary.Example(text, snippets);

        if (object.has("pos")) example.setPartOfSpeech(object.getString("pos"));

        if (object.has("gen")) example.setGender(object.getString("gen"));

        if (object.has("num")) example.setNumber(object.getString("num"));

        return example.getText() != null ? example : null;
    }

    private static List<Dictionary.Snippet> parseSnippets(JSONArray array) {
        List<Dictionary.Snippet> snippets = new ArrayList<>();

        int iterator = 0;

        while (iterator < array.length()) {
            Dictionary.Snippet snippet = parseSnippet(array.getJSONObject(iterator));

            if (snippet != null) snippets.add(snippet);

            iterator++;
        }

        return snippets;
    }

    private static Dictionary.Snippet parseSnippet(JSONObject object) {
        String text = null;

        if (object.has("text")) text = object.getString("text");

        Dictionary.Snippet snippet = new Dictionary.Snippet(text);

        if (object.has("pos")) snippet.setPartOfSpeech(object.getString("pos"));

        if (object.has("gen")) snippet.setGender(object.getString("gen"));

        if (object.has("num")) snippet.setNumber(object.getString("num"));

        return snippet.getText() != null ? snippet : null;
    }

    private static List<Dictionary.Synonym> parseSynonyms(JSONArray array) {
        List<Dictionary.Synonym> synonyms = new ArrayList<>();

        int iterator = 0;

        while (iterator < array.length()) {
            Dictionary.Synonym synonym = parseSynonym(array.getJSONObject(iterator));

            if (synonym != null) synonyms.add(synonym);

            iterator++;
        }

        return synonyms;
    }

    private static Dictionary.Synonym parseSynonym(JSONObject object) {
        String text = null;

        if (object.has("text")) text = object.getString("text");

        Dictionary.Synonym synonym = new Dictionary.Synonym(text);

        if (object.has("pos")) synonym.setPartOfSpeech(object.getString("pos"));

        if (object.has("gen")) synonym.setGender(object.getString("gen"));

        if (object.has("num")) synonym.setNumber(object.getString("num"));

        return synonym.getText() != null ? synonym : null;
    }

    private static List<Dictionary.Meaning> parseMeanings(JSONArray array) {
        List<Dictionary.Meaning> meanings = new ArrayList<>();

        int iterator = 0;

        while (iterator < array.length()) {
            Dictionary.Meaning meaning = parseMeaning(array.getJSONObject(iterator));

            if (meaning.getText() != null) meanings.add(meaning);

            iterator++;
        }

        return meanings;
    }

    private static Dictionary.Meaning parseMeaning(JSONObject object) {
        String text = null;

        if (object.has("text")) text = object.getString("text");

        Dictionary.Meaning meaning = new Dictionary.Meaning(text);

        if (object.has("pos")) meaning.setPartOfSpeech(object.getString("pos"));

        if (object.has("gen")) meaning.setGender(object.getString("gen"));

        if (object.has("num")) meaning.setNumber(object.getString("num"));

        return meaning.getText() != null ? meaning : null;
    }

}
