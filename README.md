# Yandex Linguistic Bundle 
![Yandex Linguistic Bundle Banner](images/banner.png)

Java-враппер для всех лингвистических сервисов Яндекса:

- [`Яндекс.Словарь`](src/main/java/ru/amayakasa/linguistic/YandexDictionary.java)
- [`Яндекс.Предиктор`](src/main/java/ru/amayakasa/linguistic/YandexPredictor.java)
- [`Яндекс.Спеллер`](src/main/java/ru/amayakasa/linguistic/YandexSpeller.java)
- [`Яндекс.Переводчик`](src/main/java/ru/amayakasa/linguistic/YandexTranslator.java)

Каждый из врапперов поддерживает синхронные и асинхронные вызовы к сервисам. Для обработки ответов на асинхронные вызовы используются колбэки.

Пример использования  колбэков с одним из врапперов:
```java
// Инициализируем враппер для Яндекс.Переводчика
YandexTranslator translator;

translator = new YandexTranslator(
        "your-traslator-key-here", 
        Version.TRANSLATE_LATEST, 
        ResponseInterface.TRANSLATE_JSON
);

// Делаем асинхронный запрос на определение языка текста и вешаем на него колбэк
translator.getTextLanguage("So what is the language of the text?", new YandexCallback<Language>() {

    @Override
    public void onResponse(Language response) {
      // Если запрос прошел успешно, выводим описание определенного языка
      alert(response.description);
    }

    @Override
    public void onFailure(Throwable throwable) {
      // Иначе, выводим сообщение об ошибке
      alert(throwable.getMessage());
    }
    
});
```