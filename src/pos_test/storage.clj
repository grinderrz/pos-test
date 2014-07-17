(ns pos-test.storage
  (:require monger.core
            monger.collection
            monger.operators)
  (:import [com.mongodb MongoOptions ServerAddress]))

(def put-tagged-sentence
  (let [db (monger.core/get-db (monger.core/connect) "patterns")]
    (fn [[sentence model]]
      (if (> (monger.collection/count db "model" {:model model}) 0)
        (monger.collection/update db
                                  "model"
                                  {:model model}
                                  {monger.operators/$push {:sentences sentence}
                                   monger.operators/$inc {:sc 1}}) 
        (monger.collection/insert db
                                  "model"
                                  {:model model :sentences [sentence] :sc 1})))))
