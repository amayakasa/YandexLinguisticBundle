# Yandex Linguistic Bundle

![Yandex Linguistic Bundle Banner](images/banner.png)

## Дисклеймер

Яндекс переехал в облако и полностью изменил свой API.

Скорее всего ни один из этих врапперов на 2024 год не является актуальным.

### Краткое описание
Yandex Linguistic Bundle — это набор Java-врапперов для всех лингвистических сервисов Яндекса.
Включает в себя следующие врапперы и объекты для их параметров и ответов (вся документация внутри):

- [`Враппер для Яндекс.Словаря`](src/main/java/ru/amayakasa/linguistic/YandexDictionary.java)
- [`Враппер для Яндекс.Предиктора`](src/main/java/ru/amayakasa/linguistic/YandexPredictor.java)
- [`Враппер для Яндекс.Спеллера`](src/main/java/ru/amayakasa/linguistic/YandexSpeller.java)
- [`Враппер для Яндекс.Переводчика`](src/main/java/ru/amayakasa/linguistic/YandexTranslator.java)

Каждый из врапперов поддерживает синхронные и асинхронные вызовы к сервисам, также реализованы callback-функции.

#### [`Условия использования сервисов Яндекса`](https://yandex.ru/legal/)

### План злодеяний
- [x] Задокументировать исходный код
- [x] Добавить интеграцию `CircleCI`
- [x] Написать адекватный `README.md`
- [ ] Добавить враппер `Яндекс.Переводчика` на `Яндекс.Облаке` 
- [ ] Опубликовать набор врапперов на `Maven Central`
- [ ] Отделить документацию от исходного хода
- [ ] Английская документация и `README.md`

Баннер для этого репозитория сделал [`Ar4ikov`](https://github.com/ar4ikov).

