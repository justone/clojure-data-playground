(ns clojuredata.core
  (:require [clojuredata.file :refer :all]
            [clojuredata.protocol :refer :all]))

(def storage (->FileStore "."))

(defn -main
  []
  (println storage)
  (store storage "key" "Molly"))
