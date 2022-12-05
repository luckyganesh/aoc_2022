(ns aoc-2022.day2
  (:require [aoc-2022.utils :as utils]))

(def input (slurp "resources/day2.txt"))
(def small-input "A Y
B X
C Z")

(defn parse-input [input]
  (map utils/split-by-space (utils/split-into-lines input)))

(def opponent-move-abc-mapping {"A" :Rock "B" :Paper "C" :Scissors})
(def my-move-xyz-mapping {"X" :Rock "Y" :Paper "Z" :Scissors})
(def result-xyz-mapping {"X" :Lose "Y" :Draw "Z" :Win})

(def shape-values {:Rock 1 :Paper 2 :Scissors 3})
(def result-values {:Win 6 :Draw 3 :Lose 0})

(defn get-result [opponent-shape my-shape]
  (condp = [my-shape opponent-shape]
     [my-shape my-shape]  :Draw
     [:Rock :Scissors]  :Win
     [:Scissors :Paper]  :Win
     [:Paper :Rock]  :Win
     :Lose))

(defn sol-1 [opponent-move my-move]
  (let [opponent-shape (get opponent-move-abc-mapping opponent-move)
        my-shape (get my-move-xyz-mapping my-move)
        result (get-result opponent-shape my-shape)]
    (+ (get shape-values my-shape) (get result-values result))))

(defn part-1 [input]
  (->> input
       parse-input
       (map (partial apply sol-1))
       (apply +)))

(def lookup
  {:Rock {:Lose :Scissors :Draw :Rock :Win :Paper}
   :Paper {:Lose :Rock :Draw :Paper :Win :Scissors}
  :Scissors {:Lose :Paper :Draw :Scissors :Win :Rock}
   })

(defn sol-2 [opponent-move encrypted-result]
  (let [opponent-shape (get opponent-move-abc-mapping opponent-move)
        result (get result-xyz-mapping encrypted-result)
        my-shape (result (opponent-shape lookup))]
    (+ (get shape-values my-shape) (get result-values result))))

(defn part-2 [input]
  (->> input
       parse-input
       (map (partial apply sol-2))
       (apply +)))

(defn result [] [(part-1 input) (part-2 input)])