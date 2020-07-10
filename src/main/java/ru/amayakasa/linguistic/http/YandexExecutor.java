package ru.amayakasa.linguistic.http;

import okhttp3.ResponseBody;
import retrofit2.Response;
import ru.amayakasa.linguistic.parameters.ResponseInterface;
import ru.amayakasa.linguistic.parameters.Version;

import java.text.MessageFormat;

/**
 * Основа для объектов Яндекс.Переводчика, Яндекс.Словаря, Яндекс.Предиктора и Яндекс.Спеллера.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexPredictor}   — Яндекс.Предиктор;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */
public abstract class YandexExecutor {

    private YandexService service;

    private String key;
    private Version version;
    private ResponseInterface responseInterface;

    /**
     * Геттер для получения интерфейса реализуемого сервиса.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     */
    protected YandexService getService() {
        return service;
    }

    /**
     * Геттер для получения ключа реализуемого сервиса.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     */
    protected String getKey() {
        return key;
    }

    /**
     * Геттер для получения версии реализуемого сервиса.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Version}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Геттер для получения интерфейса ответа реализуемого сервиса.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.ResponseInterface}
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     */
    public ResponseInterface getResponseInterface() {
        return responseInterface;
    }

    public YandexExecutor(String key, Version version, ResponseInterface responseInterface) {
        this.key = key;
        this.version = version;
        this.responseInterface = responseInterface;

        this.service = this.setupService();
    }

    /**
     * Формирование HTTP-клиента Retrofit2 для определенного сервиса.
     * См. подробнее {@link ru.amayakasa.linguistic.http.YandexService}
     */
    protected abstract YandexService setupService();

    /**
     * Валидация ответа от Яндекс.Переводчика, Яндекс.Словаря, Яндекс.Предиктора и Яндекс.Спеллера.
     * <p>
     * Если код ответа не обнаружен среди всех заранее известных, то метод выбросит исключение.
     * Так же, если код ответа отличается от 200 (OK), то метод тоже выбросит исключение,
     * но сообщение исключения будет обработано со стороны данного враппера.
     * Если тело ответа пустое, то метод выбросит исключение с сообщением о пустом теле ответа.
     * <p>
     * См. подробнее {@link ru.amayakasa.linguistic.http.ResponseCode} — код ответа.
     *
     * @param response ответ подлежащий валидации;
     * @throws Exception исключение выбрасиваемое в вышеописанных случаях.
     */
    protected void validateResponse(Response<ResponseBody> response) throws Exception {
        ResponseCode responseCode = ResponseCode.byCode(response.code());

        if (responseCode == null) throw new Exception(
                MessageFormat.format("{0} - {1}", response.code(), response.message())
        );

        else if (responseCode.compareTo(ResponseCode.OK) != 0) throw new Exception(
                MessageFormat.format("{0} - {1}", responseCode.code, responseCode.description)
        );

        if (response.body() == null) throw new Exception("Empty response body");
    }
}
