(ns aoc-2022.day5
  (:require
    [aoc-2022.utils :as utils]
    [clojure.string :as str]))

(def input (slurp "resources/day5.txt"))

(def small-input
"    [D]
[N] [C]
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

(defn tag-action [action step]
  [(keyword action) (utils/parse-to-int step)])

(defn parse-move [move]
  (->> move
       (utils/split #" ")
       (partition 2)
       (map (partial apply tag-action))
       (into {})))


(defn parse-input [input]
  (let [separated-input (->> input
                             str/split-lines
                             (partition-by (partial = "")))
        parsed-stacks (->> separated-input
                           first
                           (map (partial partition-all 4))
                           (map (partial map (comp str second))))
        count-of-stacks (count (last parsed-stacks))
        stacks (->> parsed-stacks
                    drop-last
                    (map reverse)
                    (map (partial apply conj (repeat count-of-stacks " ")))
                    (map (partial take count-of-stacks))
                    (apply mapv vector)
                    (mapv (partial filter (partial not= " ")))
                    (zipmap (iterate inc 1)))
        moves (->> separated-input
                   last
                   (map parse-move))
        ]
    [stacks moves]))

(defn update-the-stacks-9000 [stacks instruction]
  (let [containers-to-load  (take (:move instruction) (get stacks (:from instruction)))
        unloaded-stack (drop (:move instruction) (get stacks (:from instruction)))
        loaded-stack (apply conj (get stacks (:to instruction)) containers-to-load)
        updated-stacks (-> stacks
                          (assoc (:from instruction) unloaded-stack)
                          (assoc (:to instruction) loaded-stack))]
    updated-stacks))

(defn update-the-stacks-9001 [stacks instruction]
  (let [containers-to-load  (take (:move instruction) (get stacks (:from instruction)))
        unloaded-stack (drop (:move instruction) (get stacks (:from instruction)))
        loaded-stack (apply conj (get stacks (:to instruction)) (reverse containers-to-load))
        updated-stacks (-> stacks
                           (assoc (:from instruction) unloaded-stack)
                           (assoc (:to instruction) loaded-stack))]
    updated-stacks))

(defn part-1 [input]
  (let [[stacks, moves] (parse-input input)]
    (->> moves
         (reduce update-the-stacks-9000 stacks)
         (into (sorted-map))
         (map second)
         (map first)
         str/join
         )))

(defn part-2 [input]
  (let [[stacks, moves] (parse-input input)]
    (->> moves
         (reduce update-the-stacks-9001 stacks)
         (into (sorted-map))
         (map second)
         (map first)
         str/join
         )))

;(println (parse-input input))
(defn result []
  [(part-1 input) (part-2 input)])