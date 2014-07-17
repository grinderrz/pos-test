(ns pos-test.core
  (:require [pos-test.parser :as parser]
            [pos-test.storage :as storage]
            [clojure.java.jdbc :as jdbc])
  (:gen-class))

(defn process-text [text]
  (doall (map storage/put-tagged-sentence
              (parser/tag-text text))))

(defn from-file [filename]
  (process-text (slurp filename)))

(defn from-mysql [db-spec]
  (jdbc/query db-spec ["select count(*) from content;"]
              :result-set-fn (fn [rs]
                               (doall (map (fn [row]
                                             (process-text (:content_text row))))))))

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

