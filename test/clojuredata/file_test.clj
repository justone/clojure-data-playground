(ns clojuredata.file-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojuredata.file :refer :all]
            [clojuredata.protocol :refer :all]))

; TODO: shared object is bad, make a separate storage object for each test with a tempdir
(def storage (->FileStore "."))

(deftest store-retrieve-test
  (testing "Can I store and retrieve strings."
    (remove-all storage)
    (store storage "testkey" "string")
    (store storage :testkey2 "string2")
    (is (= "string" (retrieve storage "testkey")))
    (is (= "string2" (retrieve storage :testkey2)))
    ))

(deftest export-test
  (testing "Can I export the entire data set."
    (remove-all storage)
    (store storage "testkey" "string")
    (store storage "testkey2" {:name "foo"})
    (is (= {:testkey "string" :testkey2 {:name "foo"}} (export-all storage)))))

(deftest import-test
  (testing "Can I import the entire data set."
    (remove-all storage)
    (import-all storage {:testkey "string" :testkey2 {:name "foo"}})
    (is (retrieve storage "testkey") "string")
    (is (retrieve storage "testkey2") {:name "foo"})))
