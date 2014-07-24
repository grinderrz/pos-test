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
  (doall
    (take 200000
          (for [id (range)]
            (jdbc/query db-spec
              ["select content_text from content where content_id = ?;" id]
              :row-fn (fn [row]
                        (process-text (:content_text row))))))))

(defn -main
  [& args]
  (let [input-type (first args)]
    (cond
      (= input-type "--file")
        (from-file (nth args 1))
      (= input-type "--db")
        (let [[host db user pass] (rest args)]
          (from-mysql
            {:classname "com.mysql.jdbc.Driver"
             :subprotocol "mysql"
             :subname (str "//" host ":3306/" db)
             :user user
             :password pass}))
        )))

