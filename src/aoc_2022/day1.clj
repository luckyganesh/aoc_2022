(ns aoc-2022.day1
  (:require [aoc-2022.utils :as utils]))

(def input (slurp "resources/day1.txt"))

(def small-input "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")


(defn parse-input [input]
  (filter (partial not= '("")) (partition-by empty? (utils/split-into-lines input))))

(defn solution [input n]
  (->> input
       parse-input
       (map (partial map utils/parse-to-int))
       (map (partial apply +))
       sort
       (take-last n)
       (apply +)))

(defn part-1 [input]
  (solution input 1))

(defn part-2 [input]
  (solution input 3))

(defn result [] [(part-1 input) (part-2 input)])