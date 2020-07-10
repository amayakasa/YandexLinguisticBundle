package ru.amayakasa.linguistic.response;

import java.util.List;

/**
 * Объект-враппер для представления ответов Яндекс.Словаря в удобной форме.
 * <p>
 * См. подробнее {@link ru.amayakasa.linguistic.YandexDictionary}  — Яндекс.Словарь.
 *
 * @author Amayakasa
 */
public class Dictionary {

    private List<Definition> definitions;

    public Dictionary(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public boolean isEmpty() {
        return getDefinitions().isEmpty();
    }

    public static class Attribute {
        protected String text = "";
        private String number = "";
        private String gender = "";
        private String partOfSpeech = "";

        public String getText() {
            return text;
        }

        public String getNumber() {
            return number;
        }

        public String getGender() {
            return gender;
        }

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setPartOfSpeech(String partOfSpeech) {
            this.partOfSpeech = partOfSpeech;
        }
    }

    public static class Definition extends Attribute {
        private String transcription = "";

        private List<Translation> translations;

        public Definition(String text, List<Translation> translations) {
            this.text = text;
            this.translations = translations;
        }

        public String getTranscription() {
            return transcription;
        }

        public List<Translation> getTranslations() {
            return translations;
        }

        public void setTranscription(String transcription) {
            this.transcription = transcription;
        }
    }

    public static class Translation extends Attribute {
        private List<Synonym> synonyms;
        private List<Meaning> meanings;
        private List<Example> examples;

        public Translation(String text, List<Synonym> synonyms, List<Meaning> meanings, List<Example> examples) {
            this.text = text;
            this.synonyms = synonyms;
            this.meanings = meanings;
            this.examples = examples;
        }

        public List<Synonym> getSynonyms() {
            return synonyms;
        }

        public List<Meaning> getMeanings() {
            return meanings;
        }

        public List<Example> getExamples() {
            return examples;
        }
    }

    public static class Snippet extends Attribute {
        public Snippet(String text) {
            this.text = text;
        }
    }

    public static class Synonym extends Attribute {
        public Synonym(String text) {
            this.text = text;
        }
    }

    public static class Meaning extends Attribute {
        public Meaning(String text) {
            this.text = text;
        }
    }

    public static class Example extends Attribute {
        private List<Snippet> snippets;

        public Example(String text, List<Snippet> snippets) {
            this.text = text;
            this.snippets = snippets;
        }

        public List<Snippet> getSnippets() {
            return snippets;
        }
    }
}
