(ns pos-test.core
  (:require [pos-test.parser :as parser]
            [pos-test.storage :as storage]
            [clojure.java.jdbc :as jdbc])
  (:gen-class))

(defn from-file [filename]
  (let [text (slurp filename)
        tagged (parser/tag-text text)]
    (doall (map storage/put-tagged-sentence tagged))))

(defn from-mysql [db-spec]
  (println (jdbc/query db-spec ["select count(*) from content;"]))
  )

(defn -main
  [& args]
  (let [type (first args)]
    (cond
      (= type "--file")
        (from-file (nth args 1))
      (= type "--db")
        (let [[host db user pass] (rest args)]
          (from-mysql
            {:classname "com.mysql.jdbc.Driver"
             :subprotocol "mysql"
             :subname (str "//" host ":3306/" db)
             :user user
             :password pass}))
        )))

