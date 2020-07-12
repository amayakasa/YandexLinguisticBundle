package ru.amayakasa.linguistic;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.amayakasa.linguistic.response.parser.ResponseParser;
import ru.amayakasa.linguistic.response.Completion;
import ru.amayakasa.linguistic.http.YandexCallback;
import ru.amayakasa.linguistic.http.YandexExecutor;
import ru.amayakasa.linguistic.http.YandexService;
import ru.amayakasa.linguistic.parameters.Language;
import ru.amayakasa.linguistic.parameters.ResponseInterface;
import ru.amayakasa.linguistic.parameters.Version;

import java.io.IOException;
import java.util.List;

/**
 * Враппер для сервиса Яндекс.Предиктор
 * <p>
 * С помощью предиктора приложение или веб-сервис могут выводить текстовые подсказки в различных текстовом полях.
 * Это упрощает ввод, особенно на мобильных устройствах. Кроме того, Предиктор учитывает возможные опечатки.
 * Яркий пример использования — дополнение поискового запроса в поисковой строке.
 * <p>
 * Результат обращения к сервису представлен в виде объектов #Language и #Completion.
 * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
 * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
 * <p>
 * Примечание. Вызовы могут быть как синхронными, так и асинхронными.
 * Для асинхронных вызовов используйте методы, которые включают в себя #YandexCallback.
 * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
 * <p>
 * Перед использованием в своих приложениях или веб-сервисах ознакомтесь с условиями использования этого сервиса.
 * <a href="https://yandex.ru/legal/predictor_api/">Условия использования сервиса «Яндекс.Предиктор»</a>
 *
 * @author Amayakasa
 */
public class YandexPredictor extends YandexExecutor {

    /**
     * Конструктор для инициализации нового объекта #YandexPredictor.
     *
     * @param key               API-ключ Яндекс.Предиктора;
     * @param version           версия API Яндекс.Предиктора;
     * @param responseInterface форма ответа (JSON или XML).
     */
    public YandexPredictor(String key, Version version, ResponseInterface responseInterface) {
        super(key, version, responseInterface);
    }

    /**
     * Формирование HTTP-клиента для Яндекс.Предиктора.
     * См.подробнее {@link ru.amayakasa.linguistic.http.YandexExecutor}
     * См.подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     *
     * @return HTTP-клиент для Яндекс.Предиктора.
     */
    @Override
    protected YandexService setupService() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://predictor.yandex.net").build();

        return retrofit2.create(YandexService.class);
    }

    /**
     * Метод для получения списка доступных направлений для дополнения текста.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде списка объектов #Language.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
     *
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public List<Language> getPredictionDirections() throws Exception {
        Call<ResponseBody> call = generatePredictionDirectionsCall();

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parsePredictionDirections(response);
    }

    /**
     * Метод для получения списка доступных направлений для дополнения текста.
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
    public void getPredictionDirections(YandexCallback<List<Language>> callback) {
        Call<ResponseBody> call = generatePredictionDirectionsCall();

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parsePredictionDirections(response));
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
     * Метод для получения списка доступных направлений для дополнения текста.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Completion.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     *
     * @param text     текст, который нуждается в дополнении;
     * @param language язык на котором написан указанный текст;
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Completion getCompletion(String text, Language language) throws Exception {
        Call<ResponseBody> call = generateCompletionCall(language, text, 1);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseCompletion(text, response);
    }

    /**
     * Метод для получения списка доступных направлений для дополнения текста.
     * <p>
     * Этот метод создает синхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Completion.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     *
     * @param text     текст, который нуждается в дополнении;
     * @param language язык на котором написан указанный текст;
     * @param limit    максимальное кол-во вариантов дополнения;
     * @throws Exception при некорректном ответе или ошибке во время запроса.
     */
    public Completion getCompletion(String text, Language language, int limit) throws Exception {
        Call<ResponseBody> call = generateCompletionCall(language, text, limit);

        Response<ResponseBody> response = call.execute();

        validateResponse(response);

        return ResponseParser.parseCompletion(text, response);
    }

    /**
     * Метод для получения списка доступных направлений для дополнения текста.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Completion.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     текст, который нуждается в дополнении;
     * @param language язык на котором написан указанный текст;
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getCompletion(String text, Language language, YandexCallback<Completion> callback) {
        Call<ResponseBody> call = generateCompletionCall(language, text, 1);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseCompletion(text, response));
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
     * Метод для получения списка доступных направлений для дополнения текста.
     * <p>
     * Этот метод создает асинхронный запрос к вышеупомянотому сервису.
     * Ответ на указанный запрос вернется в виде объекта #Completion.
     * Для обработки ответа данного запроса, используется #YandexCallback.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexCallback}
     *
     * @param text     текст, который нуждается в дополнении;
     * @param language язык на котором написан указанный текст;
     * @param limit    максимальное кол-во вариантов дополнения;
     * @param callback колбэк для обработки ответа на запрос.
     */
    public void getCompletion(String text, Language language, int limit, YandexCallback<Completion> callback) {
        Call<ResponseBody> call = generateCompletionCall(language, text, limit);

        Callback<ResponseBody> genericCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResponse(ResponseParser.parseCompletion(text, response));
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
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getPredictionDirections}
     *
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generatePredictionDirectionsCall() {
        return getService().getPredictionDirections(getVersion(), getResponseInterface(), getKey());
    }

    /**
     * Генерирует запрос на завершение указанного текста для вышеупомянотого сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService#getCompletion}
     *
     * @param language язык на котором написан указанный текст;
     * @param text     текст к который нуждается в продолжении;
     * @param limit    максимальное кол-во вариантов продолжения;
     * @return сгенерированный запрос для HTTP-клиента.
     */
    private Call<ResponseBody> generateCompletionCall(Language language, String text, int limit) {
        return getService().getCompletion(getVersion(), getResponseInterface(), getKey(), language, text, limit);
    }
}
