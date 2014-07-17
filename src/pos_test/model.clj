(ns pos-test.model
  (:require [opennlp.nlp :as nlp]))

(def sentence-split (nlp/make-sentence-detector "models/en-sent.bin"))
(def pos-tag (nlp/make-pos-tagger "models/en-pos-maxent.bin"))
(def tokenize (nlp/make-tokenizer "models/en-token.bin"))

(defn make-model [sentence]
  (map #(% 1) (pos-tag (tokenize sentence))))

(defn tag-sentence [sentence]
  [sentence (make-model sentence)])

(defn tag-text [text]
  (map tag-sentence (sentence-split text)))

(defn analyze-text [text]
  (sort-by #(- 0 (count (% 1))) (group-by make-model (sentence-split text))))
