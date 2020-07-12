package ru.amayakasa.linguistic;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.amayakasa.linguistic.response.parser.ResponseParser;
import ru.amayakasa.linguistic.parameters.Format;
import ru.amayakasa.linguistic.response.Phrase;
import ru.amayakasa.linguistic.http.YandexCallback;
import ru.amayakasa.linguistic.http.YandexExecutor;
import ru.amayakasa.linguistic.http.YandexService;
import ru.amayakasa.linguistic.parameters.Language;
import ru.amayakasa.linguistic.parameters.ResponseInterface;
import ru.amayakasa.linguistic.parameters.Version;

import java.util.List;

/**
 * Враппер для сервиса Яндекс.Спеллер
 * <p>
 * Спеллер предлагает разработчикам использовать возможности интерактивной проверки орфографии.
 * Сервис анализирует слова, основываясь на правилах орфографии и лексике современного языка,
 * а также использует технологии машинного обучения. В настоящее время поддерживается только три языка.
 * Примечание. Пунктуационные, грамматические (ошибки согласования слов) и стилистические ошибки не исправляются.
 * <p>
 * Результат обращения к сервису представлен в виде объекта #Phrase.
 * См. подробнее {@link ru.amayakasa.linguistic.response.Phrase}
 * <p>
 * Примечание. Вызовы могут быть как синхронными, так и асинхронными.
 * Для асинхронных вызовов используйте методы, которые включают в себя #YandexCallback.
 * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
 * <p>
 * Перед использованием в своих приложениях или веб-сервисах ознакомтесь с условиями использования этого сервиса.
 * <a href="https://yandex.ru/legal/speller_api/">Условия использования сервиса «Яндекс.Спеллер»</a>
 *
 * @author Amayakasa
 */
public class YandexSpeller extends YandexExecutor {

    /**
     * Конструктор для инициализации нового объекта #YandexSpeller.
     *
     * @param version           версия API Яндекс.Спеллера;
     * @param responseInterface форма ответа (JSON или XML).
     */
    public YandexSpeller(Version version, ResponseInterface responseInterface) {
        super("speller-works-without-key", version, responseInterface);
    }

    /**
     * Формирование HTTP-клиента для Яндекс.Спеллера.
     * См.подробнее {@link ru.amayakasa.linguistic.http.YandexExecutor}
     * См.подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     *
     * @return HTTP-клиент для Яндекс.Спеллера.
     */
    @Override
    protected YandexService setupService() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://speller.yandex.net").build();

        return retrofit2.create(YandexService.class);
    }

    /**
     * Метод для проверки указанной фразы на орфографические ошибки.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Phrase.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     *
     * @param text     фраза, которая нуждается в проверке;
     * @param language язык на котором написан указанная фраза;
     * @param options  опции для спеллера (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Phrase getSpelledPhrase(String text, Language language, int... options) throws Exception {
        int spellerOptions = 0;

        for (int option : options) spellerOptions = spellerOptions + option;

        Call<ResponseBody> call = generateSpelledPhraseCall(text, language, spellerOptions, Format.PLAIN);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parsePhrase(text, response);
    }

    /**
     * Метод для проверки указанной фразы на орфографические ошибки.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Phrase.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     *
     * @param text     фраза, которая нуждается в проверке;
     * @param language язык на котором написан указанная фраза;
     * @param format   формат проверяемого текста;
     * @param options  опции для спеллера (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Phrase getSpelledPhrase(String text, Language language, Format format, int... options) throws Exception {
        int spellerOptions = 0;

        for (int option : options) spellerOptions = spellerOptions + option;

        Call<ResponseBody> call = generateSpelledPhraseCall(text, language, spellerOptions, format);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parsePhrase(text, response);
    }

    /**
     * Метод для проверки указанной фразы на орфографические ошибки.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Phrase.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     фраза, которая нуждается в проверке;
     * @param language язык на котором написан указанная фраза;
     * @param options  опции для спеллера (опционально);
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getSpelledPhrase(String text, Language language, YandexCallback<Phrase> callback, int... options) {
        int spellerOptions = 0;

        for (int option : options) spellerOptions = spellerOptions + option;

        Call<ResponseBody> call = generateSpelledPhraseCall(text, language, spellerOptions, Format.PLAIN);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parsePhrase(text, response));
                } catch (Exception exception) {
                    callback.onFailure(exception);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                callback.onFailure(throwable);
            }
        };

        call.enqueue(genericCallback);
    }

    /**
     * Метод для проверки указанной фразы на орфографические ошибки.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Phrase.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     фраза, которая нуждается в проверке;
     * @param language язык на котором написан указанная фраза;
     * @param format   формат проверяемого текста;
     * @param options  опции для спеллера (опционально);
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getSpelledPhrase(String text, Language language, Format format, YandexCallback<Phrase> callback, int... options) {
        int spellerOptions = 0;

        for (int option : options) spellerOptions = spellerOptions + option;

        Call<ResponseBody> call = generateSpelledPhraseCall(text, language, spellerOptions, format);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parsePhrase(text, response));
                } catch (Exception exception) {
                    callback.onFailure(exception);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                callback.onFailure(throwable);
            }
        };

        call.enqueue(genericCallback);
    }

    /**
     * Метод для проверки указанных фраз на орфографические ошибки.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Phrase.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     *
     * @param text     фразы, которые нуждаются в проверке;
     * @param language язык на котором написан указанные фразы;
     * @param options  опции для спеллера (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public List<Phrase> getSpelledPhrases(String[] text, Language language, int... options) throws Exception {
        int spellerOptions = 0;

        for (int option : options) spellerOptions = spellerOptions + option;

        Call<ResponseBody> call = generateSpelledPhrasesCall(text, language, spellerOptions, Format.PLAIN);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parsePhrases(text, response);
    }

    /**
     * Метод для проверки указанных фраз на орфографические ошибки.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Phrase.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     *
     * @param text     фразы, которые нуждаются в проверке;
     * @param language язык на котором написан указанные фразы;
     * @param format   формат проверяемого текста;
     * @param options  опции для спеллера (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public List<Phrase> getSpelledPhrases(String[] text, Language language, Format format, int... options) throws Exception {
        int spellerOptions = 0;

        for (int option : options) spellerOptions = spellerOptions + option;

        Call<ResponseBody> call = generateSpelledPhrasesCall(text, language, spellerOptions, format);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parsePhrases(text, response);
    }

    /**
     * Метод для проверки указанных фраз на орфографические ошибки.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Phrase.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     фразы, которые нуждаются в проверке;
     * @param language язык на котором написан указанные фразы;
     * @param options  опции для спеллера (опционально);
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getSpelledPhrases(String[] text, Language language, YandexCallback<List<Phrase>> callback, int... options) {
        int spellerOptions = 0;

        for (int option : options) spellerOptions = spellerOptions + option;

        Call<ResponseBody> call = generateSpelledPhrasesCall(text, language, spellerOptions, Format.PLAIN);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parsePhrases(text, response));
                } catch (Exception exception) {
                    callback.onFailure(exception);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                callback.onFailure(throwable);
            }
        };

        call.enqueue(genericCallback);
    }

    /**
     * Метод для проверки указанных фраз на орфографические ошибки.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Phrase.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     фразы, которые нуждаются в проверке;
     * @param language язык на котором написан указанные фразы;
     * @param format   формат проверяемого текста;
     * @param options  опции для спеллера (опционально);
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getSpelledPhrases(String[] text, Language language, Format format, YandexCallback<List<Phrase>> callback, int... options) {
        int spellerOptions = 0;

        for (int option : options) spellerOptions = spellerOptions + option;

        Call<ResponseBody> call = generateSpelledPhrasesCall(text, language, spellerOptions, format);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parsePhrases(text, response));
                } catch (Exception exception) {
                    callback.onFailure(exception);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                callback.onFailure(throwable);
            }
        };

        call.enqueue(genericCallback);
    }

    /**
     * Генерирует запрос на на проверку указанной фразы для вышеупомянотого сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getSpelledPhrase}
     *
     * @param text     фраза для проверки;
     * @param language язык на котором написана фраза;
     * @param options  опции для спеллера;
     * @param format   формат проверяемого текста;
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generateSpelledPhraseCall(String text, Language language, int options, Format format) {
        return getService().getSpelledPhrase(getResponseInterface(), text, language, options, format);
    }

    /**
     * Генерирует запрос на на проверку указанных фраз для вышеупомянотого сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getSpelledPhrases}
     *
     * @param text     фразы для проверки;
     * @param language язык на котором написана фразы;
     * @param options  опции для спеллера;
     * @param format   формат проверяемого текста;
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generateSpelledPhrasesCall(String[] text, Language language, int options, Format format) {
        return getService().getSpelledPhrases(getResponseInterface(), text, language, options, format);
    }
}
