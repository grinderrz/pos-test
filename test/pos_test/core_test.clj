(ns pos-test.core-test
  (:require [clojure.test :refer :all]
            [pos-test.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(make-model "I'll return in two minutes.")
(make-model "I'll return in 2 minutes.")
(make-model "I am the second ")
(make-model "How about this sentence?")

(map make-model (sentence-split "I am human. I am smart. And this is stupid."))
(map make-model (sentence-split "How are you. How are you? How are you!"))
(map make-model (sentence-split "I am the second player. I am the 2nd player. I am the 2 player."))

(pos-tag (tokenize "How about this sentence?"))
