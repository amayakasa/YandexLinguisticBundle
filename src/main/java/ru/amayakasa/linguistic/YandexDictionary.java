package ru.amayakasa.linguistic;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.amayakasa.linguistic.http.YandexCallback;
import ru.amayakasa.linguistic.http.YandexExecutor;
import ru.amayakasa.linguistic.http.YandexService;
import ru.amayakasa.linguistic.parameters.*;
import ru.amayakasa.linguistic.response.Dictionary;
import ru.amayakasa.linguistic.response.parser.ResponseParser;

import java.io.IOException;
import java.util.List;

/**
 * Враппер для сервиса Яндекс.Словарь
 * <p>
 * В отличие от обычных переводных словарей, Яндекс.Словарь составляется автоматически — с помощью технологий, которые
 * лежат в основе системы машинного перевода Яндекса. В статьях Словаря указано, к какой части речи относится слово,
 * а также даны сгруппированные переводы с примерами. Для английских слов приводится транскрипция.
 * Для английских, французских, немецких, испанских, итальянских и португальских слов приводится транскрипция.
 * <p>
 * Всего сервис поддерживает 90 языковых пар.
 * <p>
 * Результат обращения к сервису представлен в виде объектов #Language и #Dictionary.
 * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
 * См. подробнее {@link ru.amayakasa.linguistic.response.Dictionary}
 * <p>
 * Примечание. Вызовы могут быть как синхронными, так и асинхронными.
 * Для асинхронных вызовов используйте методы, которые включают в себя #YandexCallback.
 * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
 * <p>
 * Перед использованием в своих приложениях или веб-сервисах ознакомтесь с условиями использования этого сервиса.
 * <a href="https://yandex.ru/legal/dictionary_api/">Условия использования сервиса «Яндекс.Словарь»</a>
 *
 * @author Amayakasa
 */
public class YandexDictionary extends YandexExecutor {

    /**
     * Конструктор для инициализации нового объекта #YandexDictionary.
     *
     * @param key               API-ключ Яндекс.Словаря;
     * @param version           версия API Яндекс.Словаря;
     * @param responseInterface форма ответа (JSON или XML).
     */
    public YandexDictionary(String key, Version version, ResponseInterface responseInterface) {
        super(key, version, responseInterface);
    }

    /**
     * Формирование HTTP-клиента для Яндекс.Словаря.
     * См.подробнее {@link ru.amayakasa.linguistic.http.YandexExecutor}
     * См.подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     *
     * @return HTTP-клиент для Яндекс.Словаря.
     */
    @Override
    protected YandexService setupService() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://dictionary.yandex.net").build();

        return retrofit2.create(YandexService.class);
    }

    /**
     * Метод для получения списка доступных направлений перевода для Яндекс.Словаря.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Language.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
     *
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public List<LanguagePair> getDictionaryDirections() throws Exception {
        Call<ResponseBody> call = generateDictionaryDirectionsCall();

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseDictionaryDirections(response);
    }

    /**
     * Метод для получения списка доступных направлений перевода для Яндекс.Словаря.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #LanguagePair.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.LanguagePair}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getDictionaryDirections(YandexCallback<List<LanguagePair>> callback) {
        Call<ResponseBody> call = generateDictionaryDirectionsCall();

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseDictionaryDirections(response));
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
     * Метод для поиска слова или фразы в словаре при помощи Яндекс.Словаря.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Dictionary.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Dictionary}
     *
     * @param text     слово или фраза, для поиска словаре;
     * @param language направление перевода (пара языков);
     * @param flags    опции поиска для словаря (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Dictionary getLookup(String text, LanguagePair language, Flag... flags) throws Exception {
        int dictionaryFlag = 0;

        for (Flag flag : flags) if (flag != null) dictionaryFlag = dictionaryFlag | flag.bitmask;

        Call<ResponseBody> call = generateLookupCall(text, language, Language.ENGLISH, dictionaryFlag);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseDictionary(response);
    }

    /**
     * Метод для поиска слова или фразы в словаре при помощи Яндекс.Словаря.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Dictionary.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Dictionary}
     *
     * @param text          слово или фраза, для поиска словаре;
     * @param language      направление перевода (пара языков);
     * @param userInterface язык пользователя (опционально);
     * @param flags         опции поиска для словаря (опционально);
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Dictionary getLookup(String text, LanguagePair language, Language userInterface, Flag... flags) throws Exception {
        int dictionaryFlag = 0;

        for (Flag flag : flags) if (flag != null) dictionaryFlag = dictionaryFlag | flag.bitmask;

        Call<ResponseBody> call = generateLookupCall(text, language, userInterface, dictionaryFlag);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseDictionary(response);
    }

    /**
     * Метод для поиска слова или фразы в словаре при помощи Яндекс.Словаря.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Dictionary.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Dictionary}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     слово или фраза, для поиска словаре;
     * @param language направление перевода (пара языков);
     * @param flags    опции поиска для словаря (опционально);
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getLookup(String text, LanguagePair language, YandexCallback<Dictionary> callback, Flag... flags) {
        int dictionaryFlag = 0;

        for (Flag flag : flags) if (flag != null) dictionaryFlag = dictionaryFlag | flag.bitmask;

        Call<ResponseBody> call = generateLookupCall(text, language, Language.ENGLISH, dictionaryFlag);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseDictionary(response));
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
     * Метод для поиска слова или фразы в словаре при помощи Яндекс.Словаря.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Dictionary.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Dictionary}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Dictionary}
     *
     * @param text          слово или фраза, для поиска словаре;
     * @param language      направление перевода (пара языков);
     * @param userInterface язык пользователя (опционально);
     * @param flags         опции поиска для словаря (опционально);
     * @param callback      колбэк для обработки ответа на запрос.
     */
    public void getLookup(String text, LanguagePair language, Language userInterface, YandexCallback<Dictionary> callback, Flag... flags) {
        int dictionaryFlag = 0;

        for (Flag flag : flags) if (flag != null) dictionaryFlag = dictionaryFlag | flag.bitmask;

        Call<ResponseBody> call = generateLookupCall(text, language, userInterface, dictionaryFlag);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseDictionary(response));
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
     * Генерирует запрос на на проверку указанных фраз для вышеупомянотого сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getDictionaryDirections}
     *
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generateDictionaryDirectionsCall() {
        return getService().getDictionaryDirections(getVersion(), getResponseInterface(), getKey());
    }

    /**
     * Генерирует запрос на на проверку указанных фраз для вышеупомянотого сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getLookup}
     *
     * @param text          слово или фраза, для поиска словаре;
     * @param language      направление перевода (пара языков);
     * @param userInterface язык пользователя (опционально);
     * @param flags         опции поиска для словаря (опционально);
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generateLookupCall(String text, LanguagePair language, Language userInterface, int flags) {
        return getService().getLookup(getVersion(), getResponseInterface(), getKey(), language, text, userInterface, flags);
    }
}
