(ns pos-test.core
  (:require [pos-test.parser :as parser]
            [pos-test.storage :as storage]
            [pos-test.server :as server]
            [clojure.java.jdbc :as jdbc]
            [com.mchange.v2.c3p0.ComboPooledDataSource as cpds])
  (:gen-class))

(defn process-text [text]
  (doall (map storage/put-tagged-sentence
              (parser/tag-text text 100))))

(defn from-file [filename]
  (process-text (slurp filename)))

(defn from-mysql [db-spec]
  (doall
    (take 200000
          (let [pool {:datasource
                      (doto (cpds.)
                        (.setDriverClass (:classtame db-spec))
                        (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
                        (.setUser (:user spec))
                        (.setPassword (:password spec))
                        (.setMaxIdleTimeExcessConnections  (* 30 60))
                        (.setMaxIdleTime  (* 3 60 60)))}]
            (for [id (range)]
              (do
                (if (= 0 (mod id 1000))
                  (println id))
                (jdbc/query pool
                ["select content_text from content where content_id = ?;" id]
                :row-fn (fn [row]
                          (process-text (:content_text row))))))))))

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
      (= input-type "--serve")
        (let [port (first (rest args))]
          (server/run port)
          )
        )))

