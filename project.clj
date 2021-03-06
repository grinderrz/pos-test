(defproject pos-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clojure-opennlp "0.3.2"]
                 [com.novemberain/monger "2.0.0"]
                 [org.clojure/java.jdbc "0.3.4"]
                 [mysql/mysql-connector-java "5.1.25"]
                 [compojure "1.1.8"]
                 [ring/ring-core "1.3.0"]
                 [ring/ring-jetty-adapter "1.3.0"]
                 [com.mchange/c3p0 "0.9.2.1"]]
  :main ^:skip-aot pos-test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
