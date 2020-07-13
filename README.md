# Yandex Linguistic Bundle [![CircleCI](https://circleci.com/gh/amayakasa/YandexLinguisticBundle.svg?style=svg)](https://circleci.com/gh/amayakasa/YandexLinguisticBundle)

![Yandex Linguistic Bundle Banner](images/banner.png)

Java-враппер для всех лингвистических сервисов Яндекса:

- [`Яндекс.Словарь`](src/main/java/ru/amayakasa/linguistic/YandexDictionary.java)
- [`Яндекс.Предиктор`](src/main/java/ru/amayakasa/linguistic/YandexPredictor.java)
- [`Яндекс.Спеллер`](src/main/java/ru/amayakasa/linguistic/YandexSpeller.java)
- [`Яндекс.Переводчик`](src/main/java/ru/amayakasa/linguistic/YandexTranslator.java)

Каждый из врапперов поддерживает синхронные и асинхронные вызовы к сервисам. 
Для обработки ответов на асинхронные вызовы используются колбэки.

Перед использованием, пожалуйста, ознакомтесь с условиями использования этого сервиса.