(ns clojuredata.protocol)

(defprotocol DataStore
  "A protocol for data storage"
  (store [this k data] "Store a data structure at the specified key k.")
  (retrieve [this k] "Retrieve a data structure for the specified key k.")
  (export-all [this] "Export all data.")
  (import-all [this alldata] "Import all data.")
  (remove-all [this] "Remove all data."))
