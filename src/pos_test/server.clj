(ns pos-test.server
  (:require [compojure.core :as cc])
  (:require [compojure.route :as route])
  (:require [ring.adapter.jetty :as jetty])
  (:require [ring.adapter.jetty :as jetty]))

(cc/defroutes app
  (cc/GET / [] "")
  )

(defn run [port]
  (jetty/run-jetty app {:port port})
  )
