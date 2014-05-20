(ns clojuredata.redis
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clojuredata.protocol :as proto]))

(defn key->map
  [conn k]
  {(keyword k) (car/wcar conn (car/get (name k)))})

(defrecord RedisStore [conn-opts]
  proto/DataStore
  (store
    [this k data]
    (car/wcar (:conn-opts this) (car/set (name k) data)))
  (retrieve
    [this k]
    (car/wcar (:conn-opts this) (car/get (name k))))
  (export-all
    [this]
    (apply merge {} (map #(key->map (:conn-opts this) %) (car/wcar (:conn-opts this) (car/keys "*")))))
  (import-all
    [this alldata]
    (doseq
      [d (seq alldata)]
      (car/wcar (:conn-opts this) (car/set (name (first d)) (second d)))))
  (remove-all
    [this]
    (car/wcar (:conn-opts this) (car/flushdb))))
