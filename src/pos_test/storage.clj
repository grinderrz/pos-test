(ns pos-test.storage
  (:require monger.core
            monger.collection
            monger.operators
            monger.query)
  (:import [com.mongodb MongoOptions ServerAddress]))

(def ^{:private true}
  db (monger.core/get-db (monger.core/connect) "patterns"))

(def ^{:private true} coll "model2")

(defn put-tagged-sentence [[sentence model]]
  (if (> (monger.collection/count db coll {:model model}) 0)
    (monger.collection/update db
                              coll
                              {:model model}
                              {monger.operators/$push {:sentences sentence}
                               monger.operators/$inc {:sc 1}})
    (monger.collection/insert db
                              coll
                              {:model model :sentences [sentence] :sc 1})))

(defn find-positioned [limit offset]
  (monger.query/with-collection db coll
    (monger.query/find {})
    (monger.query/fields [:sc :model :sentences])
    (monger.query/sort (array-map :sc -1))
    (monger.query/limit limit)
    (monger.query/skip offset)))

(defn disp-mongo-model
  ([mongo-model num-sentences]
    {:count (mongo-model :sc)
     :model (mongo-model :model)
     :examples (take num-sentences (mongo-model :sentences))})
  ([mongo-model] (disp-mongo-model mongo-model 5))
  )
