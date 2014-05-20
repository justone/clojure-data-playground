(ns clojuredata.redis-test
  (:require [clojure.test :refer [deftest testing is]]
            [clojuredata.redis :refer :all]
            [clojuredata.protocol :refer :all]))

(deftest store-retrieve-test
  (testing "Can I store and retrieve strings."
    (let [storage (->RedisStore {:pool {} :spec {:db 1}})]
      (remove-all storage)
      (store storage "testkey" "string")
      (store storage :testkey2 "string2")
      (is (= "string" (retrieve storage "testkey")))
      (is (= "string2" (retrieve storage :testkey2))))))

(deftest export-test
  (testing "Can I export the entire data set."
    (let [storage (->RedisStore {:pool {} :spec {:db 2}})]
      (remove-all storage)
      (store storage "testkey" "string")
      (store storage "testkey2" {:name "foo"})
      (is (= {:testkey "string" :testkey2 {:name "foo"}} (export-all storage))))))

(deftest import-test
  (testing "Can I import the entire data set."
    (let [storage (->RedisStore {:pool {} :spec {:db 3}})]
      (remove-all storage)
      (import-all storage {:testkey "string" :testkey3 {:name "foo"}})
      (is (retrieve storage "testkey") "string")
      (is (retrieve storage "testkey3") {:name "foo"}))))
