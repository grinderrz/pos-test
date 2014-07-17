(ns pos-test.core
  (:require [pos-test.model :as model]
             monger.core
             monger.collection
             monger.operators
             )
  (:import [com.mongodb MongoOptions ServerAddress])
  (:gen-class))

(def store
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

(defn -main
  [& args]
  (from-file (first args))
  )

(defn from-file [filename]
  (let [text (slurp filename)
        tagged (model/tag-text text)]
    (doall (map store tagged))))

(defn from-mysql []
  
  )
