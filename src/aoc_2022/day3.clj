(ns aoc-2022.day3
  (:require [clojure.string :as str]))

(def input (slurp "resources/day3.txt"))

(def small-input "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw")

(def unicode-mapping "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn split [separator string]
  (str/split string separator))

(def split-into-lines (partial split #"\n"))
(def split-into-characters (partial split #""))

(defn divide-into-equal-partitions [string]
  (let [array (split-into-characters string)
        size (count array)
        half-size (/ size 2)] (map vec (split-at half-size array))))

(defn get-common-element [racks]
  (first (apply clojure.set/intersection (map set racks))))

(defn get-unicode-mapping-number [character] (inc (str/index-of unicode-mapping character)))


(defn part-1 [input]
  (transduce (map (comp get-unicode-mapping-number
                        get-common-element
                        divide-into-equal-partitions)) + 0 (split-into-lines input)))

(defn part-2 [input]
  (transduce (map (comp get-unicode-mapping-number
                        get-common-element)) + 0 (partition 3 (map split-into-characters (split-into-lines input)))))

(defn result [] [(part-1 input) (part-2 input)])