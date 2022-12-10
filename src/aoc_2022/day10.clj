(ns aoc-2022.day10
  (:require [aoc-2022.utils :as utils]
            [clojure.string :as str]))

(def input (slurp "resources/day10.txt"))

(def small-input "addx 15\naddx -11\naddx 6\naddx -3\naddx 5\naddx -1\naddx -8\naddx 13\naddx 4\nnoop\naddx -1\naddx 5\naddx -1\naddx 5\naddx -1\naddx 5\naddx -1\naddx 5\naddx -1\naddx -35\naddx 1\naddx 24\naddx -19\naddx 1\naddx 16\naddx -11\nnoop\nnoop\naddx 21\naddx -15\nnoop\nnoop\naddx -3\naddx 9\naddx 1\naddx -3\naddx 8\naddx 1\naddx 5\nnoop\nnoop\nnoop\nnoop\nnoop\naddx -36\nnoop\naddx 1\naddx 7\nnoop\nnoop\nnoop\naddx 2\naddx 6\nnoop\nnoop\nnoop\nnoop\nnoop\naddx 1\nnoop\nnoop\naddx 7\naddx 1\nnoop\naddx -13\naddx 13\naddx 7\nnoop\naddx 1\naddx -33\nnoop\nnoop\nnoop\naddx 2\nnoop\nnoop\nnoop\naddx 8\nnoop\naddx -1\naddx 2\naddx 1\nnoop\naddx 17\naddx -9\naddx 1\naddx 1\naddx -3\naddx 11\nnoop\nnoop\naddx 1\nnoop\naddx 1\nnoop\nnoop\naddx -13\naddx -19\naddx 1\naddx 3\naddx 26\naddx -30\naddx 12\naddx -1\naddx 3\naddx 1\nnoop\nnoop\nnoop\naddx -9\naddx 18\naddx 1\naddx 2\nnoop\nnoop\naddx 9\nnoop\nnoop\nnoop\naddx -1\naddx 2\naddx -37\naddx 1\naddx 3\nnoop\naddx 15\naddx -21\naddx 22\naddx -6\naddx 1\nnoop\naddx 2\naddx 1\nnoop\naddx -10\nnoop\nnoop\naddx 20\naddx 1\naddx 2\naddx 2\naddx -6\naddx -11\nnoop\nnoop\nnoop")

(defn parse-instruction [instruction]
  (let [splitted-instruction (utils/split-by-space instruction)
        command (first splitted-instruction)
        parameters (map utils/parse-to-int (rest splitted-instruction))]
    {:Command (keyword command) :parameters parameters}))

(def parse-input (comp (partial map parse-instruction) utils/split-into-lines))

(def initial-state {:X 1 :X-over-cycles []})

(defn noop-operation [state]
  (update-in state [:X-over-cycles] #(conj % (:X state))))

(defn addx-operation [parameters state]
  (let [updated-state ((comp noop-operation noop-operation) state)
        value-to-add (first parameters)
        new-X (+ value-to-add (:X state))]
    (assoc-in updated-state [:X] new-X)))

(defn perform-operation [state instruction]
  (condp = (:Command instruction)
    :noop (noop-operation state)
    :addx (addx-operation (:parameters instruction) state)))

(defn get-result-part1 [x-over-cycles]
  (apply + (map #(* (get x-over-cycles (dec %)) %) (take-while (partial >= 220) (iterate (partial + 40) 20)))))

(defn part-1 [input]
  (let [instructions (parse-input input)
        final-state (reduce perform-operation initial-state instructions)]
    (get-result-part1 (:X-over-cycles final-state))))

(defn something [i sprite]
  (let [index (inc i)
        result (if (or (= index sprite) (= index (inc sprite)) (= index (+ 2 sprite))) "#" ".")]
    result))

(defn part-2 [input]
  (let [instructions (parse-input input)
        final-state (reduce perform-operation initial-state instructions)]
    (map (partial str/join "") (map (partial map-indexed something) (partition 40 (:X-over-cycles final-state))))))

(defn result []
  [(part-1 input) (part-2 input)])