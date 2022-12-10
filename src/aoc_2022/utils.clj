(ns aoc-2022.utils
  (:require [clojure.string :as str]))



(defn split [separator string]
  (str/split string separator))

(def split-into-lines (partial split #"\n"))
(def split-by-comma (partial split #","))
(def split-by-dash (partial split #"-"))
(def split-into-characters (partial split #""))
(def split-by-space (partial split #" "))

(defn parse-to-int [x] (Integer/parseInt x))

(defn map-reduce [fun init coll]
  (loop [p1 init coll coll result []]
    (if (empty? coll) result
                      (let [p2 (first coll)
                            rest-coll (rest coll)
                            r (fun p1 p2)]
                        (recur r rest-coll (conj result r))))))
