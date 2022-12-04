(ns aoc-2022.day3
  (:require [clojure.string :as str]
            [aoc-2022.utils :as utils]
            ))

(def input (slurp "resources/day3.txt"))

(def small-input "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw")

(def unicode-mapping " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn divide-into-equal-partitions [string]
  (let [array (utils/split-into-characters string)
        size (count array)
        half-size (/ size 2)] (map vec (split-at half-size array))))

(defn get-common-element [racks]
  (first (apply clojure.set/intersection (map set racks))))

(defn get-unicode-mapping-number [character] (str/index-of unicode-mapping character))

(defn part-1 [input]
  (transduce (map (comp get-unicode-mapping-number
                        get-common-element
                        divide-into-equal-partitions)) + 0 (utils/split-into-lines input)))

(defn part-2 [input]
  (transduce (map (comp get-unicode-mapping-number
                        get-common-element)) + 0 (partition 3 (map utils/split-into-characters (utils/split-into-lines input)))))

(defn result [] [(part-1 input) (part-2 input)])