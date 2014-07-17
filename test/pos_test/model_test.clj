(ns pos-test.model-test
  (:require [clojure.test :refer :all]
            [pos-test.model :refer :all]))

(make-model "I'll return in two minutes.")
(make-model "I'll return in 2 minutes.")
(make-model "I am the second ")
(make-model "How about this sentence?")

(map make-model (sentence-split "I am human. I am smart. And this is stupid."))
(map make-model (sentence-split "How are you. How are you? How are you!"))
(map make-model (sentence-split "I am the second player. I am the 2nd player. I am the 2 player."))

(pos-tag (tokenize "How about this sentence?"))

(tag-text "This is a text. It will be parsed.")

(analyze-text "This is a text. It will be splitted to sentences. Then grouped by models. This is a sentence.")
