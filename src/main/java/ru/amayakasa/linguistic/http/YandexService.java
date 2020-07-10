package ru.amayakasa.linguistic.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.amayakasa.linguistic.parameters.*;

/**
 * Описание методов Яндекс.Переводчика, Яндекс.Словаря, Яндекс.Предиктора и Яндекс.Спеллера.
 * Каждый из метод описан в подробностях, с объяснением его параметров и запросов.
 * В краткой форме разобраны ответы каждого из перечисленных методов,
 * а также их дальнейшее представление в коде, благодаря объектам данного враппера.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexTranslator}  — Яндекс.Переводчик;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexPredictor}   — Яндекс.Предиктор;
 * См. подробнее {@link ru.amayakasa.linguistic.YandexSpeller}     — Яндекс.Спеллер.
 *
 * @author Amayakasa
 */

public interface YandexService {

    /**
     * Запрос на получения списка доступных языков для Яндекс.Предиктора.
     * Запрос вернет список языков в виде кодов языка ["код языка"].
     * Например: ["ru","en","pl","uk","de","fr","es","it","tr"].
     * <p>
     * В рамках данного враппера могут быть представлены в качестве списка объектов #Language.
     * {@link ru.amayakasa.linguistic.parameters.Language}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param version           версия API Яндекс.Предиктора;
     * @param responseInterface форма ответа (JSON или XML);
     * @param key               API-ключ Яндекс.Предиктора;
     * @return сформированный GET-запрос к Яндекс.Предиктору.
     */
    @GET("/api/{version}/{interface}/getLangs")
    Call<ResponseBody> getPredictionDirections(
            @Path("version") Version version,
            @Path("interface") ResponseInterface responseInterface,
            @Query("key") String key
    );

    /**
     * Запрос на получения списка доступных направлений перевода для Яндекс.Словаря.
     * Запрос вернет список языковых пар для перевода в форме ["код языка-код языка"].
     * Например: ["ru-ru","ru-en","ru-pl","ru-uk","en-en","en-de"].
     * <p>
     * В рамках данного враппера могут быть представлены в качестве списка объектов #LanguagePair.
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.LanguagePair}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param version           версия API Яндекс.Словаря;
     * @param responseInterface форма ответа (JSON или XML);
     * @param key               API-ключ Яндекс.Словаря;
     * @return сформированный GET-запрос к Яндекс.Словарю.
     */
    @GET("/api/{version}/{interface}/getLangs")
    Call<ResponseBody> getDictionaryDirections(
            @Path("version") Version version,
            @Path("interface") ResponseInterface responseInterface,
            @Query("key") String key
    );

    /**
     * Запрос на получения списка доступных языков для Яндекс.Переводчика.
     * Запрос вернет список языков в форме ["код языка":"название языка"].
     * Название языка зависит от языка пользователя, если выбран английский,
     * то и названия языков следовательно вернутся на английском языке.
     * Например: ["ru":"русский","en":"английский","fr":"французский"].
     * <p>
     * В рамках данного враппера могут быть представлены в качестве списка объектов #Language.
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param version           версия API Яндекс.Переводчика;
     * @param responseInterface форма ответа (JSON или XML);
     * @param key               API-ключ Яндекс.Переводчика;
     * @param userInterface     язык пользователя;
     * @return сформированный GET-запрос к Яндекс.Переводчику.
     */
    @GET("/api/{version}/{interface}/getLangs")
    Call<ResponseBody> getTranslationDirections(
            @Path("version") Version version,
            @Path("interface") ResponseInterface responseInterface,
            @Query("key") String key,
            @Query("ui") Language userInterface
    );

    /**
     * Запрос на проверку фразы в Яндекс.Спеллере.
     * Запрос вернет ответ в том случае, если в фразе были найдены ошибки.
     * Для каждого слова с ошибкой будет представлен своим блоком:
     * "code": 1 — где целое число, это значение грамматической ошибки;
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.SpellingMistake}
     * "pos": 0 — где целое число, это позиция слова с ошибкой;
     * "row": 0 — где целое число, это номер строки, в которой найдена ошибка;
     * "col": 0 — где целое число, это номер столбца, в котором найдена ошибка;
     * "len": 12 — где целое число, это длина слова, в котором найдена ошибка;
     * "word": "абстракттнчй" - исходное слово, в котором были найдены ошибки;
     * "s": ["абстрактный", ... ,"абстрактнче"] - список возможных значений.
     * <p>
     * Блоков ошибок может быть несколько штук или же они могут вовсе отсутствовать.
     * В случае их отсутствия ответ будет пустым, о чем и было сказано в начале.
     * <p>
     * В рамках данного враппера могут быть представлены в качестве объекта #Phrase,
     * который в свою очередь включает в себя список слов с ошибками и всю информацию.
     * См. подробнее {@link ru.amayakasa.linguistic.response.Phrase}
     * См. подробнее {@link ru.amayakasa.linguistic.response.Phrase.Word}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param responseInterface форма ответа (JSON или XML);
     * @param phrase            фраза для проверки;
     * @param language          язык на котором написана фраза;
     * @param options           опции для спеллера (опционально);
     * @param format            формат проверяемого текста (опционально);
     * @return сформированный GET-запрос к Яндекс.Спеллеру.
     */
    @GET("/services/{interface}/checkText")
    Call<ResponseBody> getSpelledPhrase(
            @Path("interface") ResponseInterface responseInterface,
            @Query("text") String phrase,
            @Query("lang") Language language,
            @Query("options") int options,
            @Query("format") Format format
    );

    /**
     * Запрос на проверку фраз в Яндекс.Спеллере.
     * Запрос вернет ответ в том случае, если в фразе были найдены ошибки.
     * <p>
     * Для каждой фразы будет представлен блок который будет содержать
     * блоки с ошибками, если таковые будут найдены в составе фразы.
     * <p>
     * "code": 1 — где целое число, это значение грамматической ошибки;
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.SpellingMistake}
     * "pos": 0 — где целое число, это позиция слова с ошибкой;
     * "row": 0 — где целое число, это номер строки, в которой найдена ошибка;
     * "col": 0 — где целое число, это номер столбца, в котором найдена ошибка;
     * "len": 12 — где целое число, это длина слова, в котором найдена ошибка;
     * "word": "абстракттнчй" - исходное слово, в котором были найдены ошибки;
     * "s": ["абстрактный", ... ,"абстрактнче"] - список возможных значений.
     * <p>
     * Блоков ошибок может быть несколько штук или же они могут вовсе отсутствовать.
     * В случае их отсутствия ответ будет пустым, о чем и было сказано в начале.
     * <p>
     * В рамках данного враппера могут быть представлены в качестве списка объектов #Phrase,
     * которые в свою очередь включают в себя список слов с ошибками и всю информацию.
     * См. подробнее {@link ru.amayakasa.linguistic.response.Phrase}
     * См. подробнее {@link ru.amayakasa.linguistic.response.Phrase.Word}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param responseInterface форма ответа (JSON или XML);
     * @param phrases           список фраз для проверки;
     * @param language          язык на котором написана фразы;
     * @param options           опции для спеллера (опционально);
     * @param format            формат проверяемого текста (опционально);
     * @return сформированный GET-запрос к Яндекс.Спеллеру.
     */
    @GET("/services/{interface}/checkTexts")
    Call<ResponseBody> getSpelledPhrases(
            @Path("interface") ResponseInterface responseInterface,
            @Query("text") String[] phrases,
            @Query("lang") Language language,
            @Query("options") int options,
            @Query("format") Format format
    );

    /**
     * Запрос на определение языка, на котором написан текст, при помощи Яндекс.Переводчика.
     * Запрос вернет ответ в виде кодов ответа и найденного языка.
     * "code": 200 — где целое число, это код ответа на указанный запрос;
     * См. подробнее {@link ru.amayakasa.linguistic.http.ResponseCode}
     * "lang": "en" — где строка, это код найденного языка.
     * <p>
     * В рамках данного враппера могут быть представлены в качестве объекта #Language.
     * См. подробнее {@link ru.amayakasa.linguistic.parameters.Language}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param version           версия API Яндекс.Переводчика;
     * @param responseInterface форма ответа (JSON или XML);
     * @param key               API-ключ Яндекс.Переводчика;
     * @param text              текст для определения языка;
     * @param hint              список подсказок (опционально);
     * @return сформированный GET-запрос к Яндекс.Переводчику.
     */
    @GET("/api/{version}/{interface}/detect")
    Call<ResponseBody> getTextLanguage(
            @Path("version") Version version,
            @Path("interface") ResponseInterface responseInterface,
            @Query("key") String key,
            @Query("text") String text,
            @Query("hint") String hint
    );

    /**
     * Запрос на перевод текста, на указанный язык, при помощи Яндекс.Переводчика.
     * Примечание. Все специальные символы должны быть экранированы.
     * <p>
     * Запрос вернет ответ в виде кодов ответа и языков, а также переведенный текст.
     * "code": 200 — где целое число, это код ответа на указанный запрос;
     * См. подробнее {@link ru.amayakasa.linguistic.http.ResponseCode}
     * "lang": "en-ru" — где строка, это коды языков (с языка - на язык);
     * "text": "Здравствуй, Мир!" — где строка, это переведенный текст.
     * <p>
     * В рамках данного враппера могут быть представлены в качестве объекта #Translation.
     * См. подробнее {@link ru.amayakasa.linguistic.response.Translation}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param version           версия API Яндекс.Переводчика;
     * @param responseInterface форма ответа (JSON или XML);
     * @param key               API-ключ Яндекс.Переводчика;
     * @param text              текст для дальнейшего перевода;
     * @param language          направление, на которое будет совершен перевод;
     * @param format            формат переводимого текста (опционально);
     * @param options           опции для переводчика (опционально);
     * @return сформированный GET-запрос к Яндекс.Переводчику.
     */
    @GET("/api/{version}/{interface}/translate")
    Call<ResponseBody> getTextTranslation(
            @Path("version") Version version,
            @Path("interface") ResponseInterface responseInterface,
            @Query("key") String key,
            @Query("text") String text,
            @Query("lang") String language,
            @Query("format") Format format,
            @Query("options") int options
    );

    /**
     * Запрос на дополнение указанного текста при помощи Яндекс.Предиктора.
     * Примечание. Советую не использовать длинные запросы, т.к. Яндекс.Предиктор
     * может их не обработать, лучше использовать текст длинной не более 5-8 слов.
     * <p>
     * Запрос вернет ответ в виде признака конца слова, позиции в слове,
     * а также списка возможных вариантов для дополнения указанного слова.
     * <p>
     * "endOfWord": false — где true/false, это признак конца указанного слова;
     * "pos": -1 — где целое число, это позиция в слове, которому нужно продолжение;
     * "text": ["сапогах", ... ,"сметане"] — список возможных вариантов завершения.
     * <p>
     * В рамках данного враппера могут быть представлены в качестве объекта #Completion.
     * См. подробнее {@link ru.amayakasa.linguistic.response.Completion}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param version           версия API Яндекс.Предиктора;
     * @param responseInterface форма ответа (JSON или XML);
     * @param key               API-ключ Яндекс.Предиктора;
     * @param language          язык на котором написан указанный текст;
     * @param text              текст, который нуждается в дополнении;
     * @param limit             максимальное кол-во вариантов дополнения;
     * @return сформированный GET-запрос к Яндекс.Предиктору.
     */
    @GET("/api/{version}/{interface}/complete")
    Call<ResponseBody> getCompletion(
            @Path("version") Version version,
            @Path("interface") ResponseInterface responseInterface,
            @Query("key") String key,
            @Query("lang") Language language,
            @Query("q") String text,
            @Query("limit") int limit
    );

    /**
     * Запрос на поиск слова или фразы в словаре при помощи Яндекс.Словаря.
     * Примичание. Данный сервис работает не только с переводными словарями,
     * можно получить все возможные значения искомого слова в рамках языка.
     * <p>
     * Запрос вернет ответ в виде сформированной словарной статьи, которая
     * в свою очередь будет содержать блоки статей, переводов, синонимов,
     * значений и различных примеров. Схематически это выглядит вот так:
     * Определение (часть речи — noun, произношение — taɪm) >
     * ... Текст: time
     * ... Перевод (часть речи — существительное, произношение — ?) >
     * ...... Текст: время
     * ...... Синонимы >
     * ......... Текст: раз
     * ......... Текст: тайм
     * ...... Значения >
     * ......... Текст: timing
     * ......... Текст: fold
     * ......... Текст: half
     * ...... Примеры >
     * ......... Текст: prehistoric time
     * ......... Перевод >
     * ............ Текст: доисторическое время
     * ......... Текст: hundredth time
     * ......... Перевод >
     * ............ Текст: сотый раз
     * ......... Текст: time-slot
     * ......... Перевод >
     * ............ Текст: тайм-слот
     * В реальных условиях структура ответа выглядит следующим образом:
     * "def":
     * ... "text": "time", "pos": "noun", "ts": "taɪm"
     * ... "tr":
     * ...... "text": "время", "pos": "существительное"
     * ...... "syn":
     * ......... "text": "раз"
     * ......... "text": "тайм"
     * ...... "mean":
     * ......... "text": "timing"
     * ......... "text": "fold"
     * ......... "text": "half"
     * ...... "ex":
     * ......... "text": "prehistoric time"
     * ......... "tr":
     * ............ "text": "доисторическое время"
     * ......... "text": "hundredth time"
     * ......... "tr":
     * ............ "text": "сотый раз"
     * ......... "text": "time-slot"
     * ......... "tr":
     * ............ "text": "тайм-слот"
     * <p>
     * "def": [...] — список определений найденных по запросу;
     * "tr": [...] — список переводов (вложен в определения и примеры);
     * "syn": [...] — список синонимов (вложен в определения);
     * "mean": [...] — список возможных значений определения;
     * "ex": [...] — список примеров использования определения.
     * <p>
     * В каждом из этих элементов есть атрибуты, 4 общих и 1 уникальный.
     * "text" — текст определения, перевода и т.п. (обязательный);
     * "num" — число, может принимать значение "pl" (для имен существительных);
     * "pos" — часть речи (может отсутствовать);
     * "gen" — род существительного для тех языков, где он есть (может отсутствовать);
     * <p>
     * В рамках данного враппера могут быть представлены в качестве объекта #Dictionary.
     * См. подробнее {@link ru.amayakasa.linguistic.response.Dictionary}
     * <p>
     * Примеры ответов от API описаны только для JSON-интерфейса.
     * Ответы для XML-интерфейса обладают абсолютно той же семантикой.
     *
     * @param version           версия API Яндекс.Словаря;
     * @param responseInterface форма ответа (JSON или XML);
     * @param key               API-ключ Яндекс.Словаря;
     * @param language          направление перевода (пара языков);
     * @param text              слово или фраза, для поиска словаре;
     * @param userInterface     язык пользователя (опционально);
     * @param flags             опции поиска для словаря (опционально);
     * @return сформированный GET-запрос к Яндекс.Словарю.
     */
    @GET("/api/{version}/{interface}/lookup")
    Call<ResponseBody> getLookup(
            @Path("version") Version version,
            @Path("interface") ResponseInterface responseInterface,
            @Query("key") String key,
            @Query("lang") LanguagePair language,
            @Query("text") String text,
            @Query("ui") Language userInterface,
            @Query("flags") int flags
    );
}
