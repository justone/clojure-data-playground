(ns clojuredata.file
  (:require [clojuredata.protocol :as proto]
            [clojure.string :as string]
            [clojure.java.io :as io]))

(defn files-in-dir
  "Retrieve list of files in a directory"
  [dir]
  (->> (.listFiles (io/file "."))
       (filter #(not (.isDirectory %)))
       (map #(.getName %) )))

(defn edn-files-in-dir
  [dir]
  (filter #(re-matches #".*edn$" %) (files-in-dir dir)))

(defn retrieve-from-file
  [filename]
  (read-string (slurp filename)))

(defn store-to-file
  [filename data]
  (spit filename (pr-str data)))

(defn edn-file->map
  [filename]
  (let [k (string/replace filename #".edn$" "")
        data (retrieve-from-file filename)]
    {(keyword k) data}))

(defrecord FileStore [path]
  proto/DataStore
  (store
    [this k data]
    (store-to-file (str (:path this) "/" (name k) ".edn") data))
  (retrieve
    [this k]
    (retrieve-from-file (str (:path this) "/" (name k) ".edn")))
  (export-all
    [this]
    (apply merge {} (map edn-file->map (edn-files-in-dir (:path this)))))
  (import-all
    [this alldata]
    (doseq
      [d (seq alldata)]
      (store-to-file (str (:path this) "/" (name (first d)) ".edn") (second d))))
  (remove-all
    [this]
    (map io/delete-file (edn-files-in-dir (:path this)))
    (Thread/sleep 100))
  )
