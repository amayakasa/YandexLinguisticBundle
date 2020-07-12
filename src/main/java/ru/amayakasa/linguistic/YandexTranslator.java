package ru.amayakasa.linguistic;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.amayakasa.linguistic.response.Translation;
import ru.amayakasa.linguistic.response.parser.ResponseParser;
import ru.amayakasa.linguistic.http.YandexCallback;
import ru.amayakasa.linguistic.http.YandexExecutor;
import ru.amayakasa.linguistic.http.YandexService;
import ru.amayakasa.linguistic.parameters.Format;
import ru.amayakasa.linguistic.parameters.Language;
import ru.amayakasa.linguistic.parameters.ResponseInterface;
import ru.amayakasa.linguistic.parameters.Version;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Враппер для сервиса Яндекс.Переводчик
 * <p>
 * С помощью враппера можно получить доступ к онлайн-сервису машинного перевода Яндекса.
 * Сервис поддерживает более 90 языков и умеет переводить отдельные слова и целые тексты.
 * Этот враппер позволяет встроить Яндекс.Переводчик в различные приложения или веб-сервисы.
 * Или же переводить большие объемы текста — например, техническую документацию.
 * <p>
 * Результат обращения к сервису представлен в виде объекта #Language и #Translation.
 * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
 * См. подробнее {@link ru.amayakasa.linguistic.response.Translation}
 * <p>
 * Примечание. Запросы могут быть как синхронными, так и асинхронными.
 * Для асинхронных запросов используйте методы, которые включают в себя #YandexCallback.
 * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
 * <p>
 * Перед использованием в своих приложениях или веб-сервисах ознакомтесь с условиями использования этого сервиса.
 * <a href="https://yandex.ru/legal/translate_api/">Условия использования сервиса «Яндекс.Спеллер»</a>
 *
 * @author Amayakasa
 */
public class YandexTranslator extends YandexExecutor {

    /**
     * Конструктор для инициализации нового объекта #YandexTranslator.
     *
     * @param key               API-ключ Яндекс.Переводчика;
     * @param version           версия API Яндекс.Переводчика;
     * @param responseInterface форма ответа (JSON или XML).
     */
    public YandexTranslator(String key, Version version, ResponseInterface responseInterface) {
        super(key, version, responseInterface);
    }

    /**
     * Формирование HTTP-клиента для Яндекс.Переводчика.
     * См.подробнее {@link ru.amayakasa.linguistic.http.YandexExecutor}
     * См.подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     *
     * @return HTTP-клиент для Яндекс.Спеллера.
     */
    @Override
    protected YandexService setupService() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://translate.yandex.net").build();

        return retrofit2.create(YandexService.class);
    }

    /**
     * Метод для получения списка доступных направлений перевода текста.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Language.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
     *
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public List<Language> getTranslationDirections() throws Exception {
        Call<ResponseBody> call = generateTranslationDirectionsCall();

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseTranslationDirections(response);
    }

    /**
     * Метод для получения списка доступных направлений перевода текста.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Language.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getTranslationDirections(YandexCallback<List<Language>> callback) {
        Call<ResponseBody> call = generateTranslationDirectionsCall();

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseTranslationDirections(response));
                } catch (IOException exception) {
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
     * Метод для получения языка, на котором написан указанный текст.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Language.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
     *
     * @param text      текст для определения языка;
     * @param languages список подсказок (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Language getTextLanguage(String text, Language... languages) throws Exception {
        StringBuilder hintBuilder = new StringBuilder();

        for (Language language : languages) if (language != null) hintBuilder.append(",").append(language);

        String hint = hintBuilder.toString();

        if (hint.length() > 1) hint = hint.substring(1);

        Call<ResponseBody> call = generateTextLanguageCall(text, hint);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseTextLanguage(response);
    }

    /**
     * Метод для получения языка, на котором написан указанный текст.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Language.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text      текст для определения языка;
     * @param languages список подсказок (опционально);
     * @param callback  колбэк для обработки ответа на запрос.
     */
    public void getTextLanguage(String text, YandexCallback<Language> callback, Language... languages) {
        StringBuilder hintBuilder = new StringBuilder();

        for (Language language : languages) if (language != null) hintBuilder.append(",").append(language);

        String hint = hintBuilder.toString();

        if (hint.length() > 1) hint = hint.substring(1);

        Call<ResponseBody> call = generateTextLanguageCall(text, hint);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseTextLanguage(response));
                } catch (IOException exception) {
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
     * Метод для перевода указанного текста на выбранный язык.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Translation.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Translation}
     *
     * @param text    текст для дальнейшего перевода;
     * @param to      язык, на который будет совершен перевод;
     * @param options опции для переводчика (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Translation getTextTranslation(String text, Language to, int... options) throws Exception {
        int translatorOptions = 0;

        for (int option : options) translatorOptions = translatorOptions + option;

        String language = to.code;

        Call<ResponseBody> call = generateTextTranslationCall(text, language, Format.PLAIN, translatorOptions);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseTextTranslation(text, response);
    }

    /**
     * Метод для перевода указанного текста на выбранный язык.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Translation.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Translation}
     *
     * @param text    текст для дальнейшего перевода;
     * @param to      язык, на который будет совершен перевод;
     * @param format  формат переводимого текста;
     * @param options опции для переводчика (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Translation getTextTranslation(String text, Language to, Format format, int... options) throws Exception {
        int translatorOptions = 0;

        for (int option : options) translatorOptions = translatorOptions + option;

        String language = to.code;

        Call<ResponseBody> call = generateTextTranslationCall(text, language, format, translatorOptions);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseTextTranslation(text, response);
    }

    /**
     * Метод для перевода указанного текста на выбранный язык.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Translation.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Translation}
     *
     * @param text    текст для дальнейшего перевода;
     * @param to      язык, на который будет совершен перевод;
     * @param from    язык, с которого будет совершен перевод;
     * @param format  формат переводимого текста;
     * @param options опции для переводчика (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Translation getTextTranslation(String text, Language from, Language to, Format format, int... options) throws Exception {
        int translatorOptions = 0;

        for (int option : options) translatorOptions = translatorOptions + option;

        String language = from == null ? to.toString() : MessageFormat.format("{0}-{1}", from, to);

        Call<ResponseBody> call = generateTextTranslationCall(text, language, format, translatorOptions);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseTextTranslation(text, response);
    }

    /**
     * Метод для перевода указанного текста на выбранный язык.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Translation.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Translation}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     текст для дальнейшего перевода;
     * @param to       язык, на который будет совершен перевод;
     * @param options  опции для переводчика (опционально);
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getTextTranslation(String text, Language to, YandexCallback<Translation> callback, int... options) {
        int translatorOptions = 0;

        for (int option : options) translatorOptions = translatorOptions + option;

        String language = to.code;

        Call<ResponseBody> call = generateTextTranslationCall(text, language, Format.PLAIN, translatorOptions);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseTextTranslation(text, response));
                } catch (IOException exception) {
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
     * Метод для перевода указанного текста на выбранный язык.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Translation.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Translation}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     текст для дальнейшего перевода;
     * @param to       язык, на который будет совершен перевод;
     * @param format   формат переводимого текста;
     * @param options  опции для переводчика (опционально);
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getTextTranslation(String text, Language to, Format format, YandexCallback<Translation> callback, int... options) {
        int translatorOptions = 0;

        for (int option : options) translatorOptions = translatorOptions + option;

        String language = to.code;

        Call<ResponseBody> call = generateTextTranslationCall(text, language, format, translatorOptions);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseTextTranslation(text, response));
                } catch (IOException exception) {
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
     * Метод для перевода указанного текста на выбранный язык.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Translation.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Translation}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     текст для дальнейшего перевода;
     * @param to       язык, на который будет совершен перевод;
     * @param from     язык, с которого будет совершен перевод;
     * @param format   формат переводимого текста;
     * @param options  опции для переводчика (опционально);
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getTextTranslation(String text, Language from, Language to, Format format, YandexCallback<Translation> callback, int... options) {
        int translatorOptions = 0;

        for (int option : options) translatorOptions = translatorOptions + option;

        String language = from == null ? to.toString() : MessageFormat.format("{0}-{1}", from, to);

        Call<ResponseBody> call = generateTextTranslationCall(text, language, format, translatorOptions);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseTextTranslation(text, response));
                } catch (IOException exception) {
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
     * Генерирует запрос на получение списка языковых направлений для вышеупомянотого сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getTranslationDirections}
     *
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generateTranslationDirectionsCall() {
        return getService().getTranslationDirections(getVersion(), getResponseInterface(), getKey(), Language.ENGLISH);
    }

    /**
     * Генерирует запрос на получение языка, на котором написан указанный текст для вышеупомянотого сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getTextLanguage}
     *
     * @param text текст для определения языка;
     * @param hint список подсказок (опционально);
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generateTextLanguageCall(String text, String hint) {
        return getService().getTextLanguage(getVersion(), getResponseInterface(), getKey(), text, hint);
    }

    /**
     * Генерирует запрос на перевод указанного текста на выбранное направление для вышеупомянотого сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getTextTranslation}
     *
     * @param text     текст для дальнейшего перевода;
     * @param language направление, на которое будет совершен перевод;
     * @param format   формат переводимого текста (опционально);
     * @param options  опции для переводчика (опционально);
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generateTextTranslationCall(String text, String language, Format format, int options) {
        return getService().getTextTranslation(getVersion(), getResponseInterface(), getKey(), text, language, format, options);
    }
}
