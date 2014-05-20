(ns clojuredata.core
  (:require [clojuredata.redis :refer :all]
            [clojuredata.file :refer :all]
            [clojuredata.protocol :refer :all]))

(def filestorage (->FileStore "."))
(def redisstorage (->RedisStore {:pool {} :spec {:db 1}}))

(defn -main
  []
  (println storage)

  (store filestorage "key" "Molly")

  (store redisstorage "key" {:foo "bar"})
  (println (retrieve redisstorage "key"))

  )
