(ns aoc-2022.day8
  (:require [aoc-2022.utils :as utils]))


(def input (slurp "resources/day8.txt"))

(def small-input "30373
25512
65332
33549
35390")

(defn parse-input [input]
  (->> input
       utils/split-into-lines
       (map utils/split-into-characters)
       (mapv (partial mapv utils/parse-to-int))))

(defn is-in-between [low high point]
  (and (<= low point) (<= point high)))

(defn is-in-between-coordinate [[low-x low-y] [high-x high-y] [x y]]
  (and (is-in-between low-x high-x x) (is-in-between low-y high-y y)))

(defn direction [fn1 fn2 [x y]]
  [(fn1 x) (fn2 y)])

(def north (partial direction identity dec))
(def south (partial direction identity inc))
(def west (partial direction dec identity))
(def east (partial direction inc identity))
(def nsew [north south east west])

(defn get-coordinates-till-edge [least-coordinate highest-coordinate coordinate direction]
  (take-while (partial is-in-between-coordinate least-coordinate highest-coordinate) (rest (iterate direction coordinate))))

(defn is-visible-from-any-direction [highest-edge grid coordinate direction]
  (let [all-co-ordinates-till-edge (get-coordinates-till-edge [0 0] highest-edge coordinate direction)
        tree-height (get-in grid coordinate)
        all-tree-heights (map (partial get-in grid) all-co-ordinates-till-edge)]
    (every? (partial > tree-height) all-tree-heights)))

(defn is-visible-from-any-edge [highest-edge grid coordinate]
  (some (partial is-visible-from-any-direction highest-edge grid coordinate) nsew))

(defn generate-coordinates [[x1 y1] [x2 y2]]
  (for [x (range x1 x2)
        y (range y1 y2)]
    [x y]))

(defn count-till-first-highest-or-equal-tree [x coll]
  (loop [coll coll n 0]
    (if (empty? coll) n
                      (let [element (first coll)]
                        (if (<= x element)
                          (inc n) (recur (rest coll) (inc n)))))))

(defn find-scenic-score-in-direction [least-edge highest-edge grid coordinate direction]
  (let [all-co-ordinates-till-edge (get-coordinates-till-edge least-edge highest-edge coordinate direction)
        tree-height (get-in grid coordinate)
        all-tree-heights (map (partial get-in grid) all-co-ordinates-till-edge)]
    (count-till-first-highest-or-equal-tree tree-height all-tree-heights)))

(defn find-scenic-score-coordinate [highest-edge grid coordinate]
  (apply * (map (partial find-scenic-score-in-direction [0 0] highest-edge grid coordinate) nsew)))

(defn part-1 [input]
  (let [grid (parse-input input)
        highest-x (dec (count (first grid)))
        highest-y (dec (count grid))
        highest-coordinate [highest-x highest-y]
        trees-on-edges (* 4 highest-x)
        middle-trees-co-ordinates (generate-coordinates [1 1] highest-coordinate)]
    (+ trees-on-edges (count (filter (partial is-visible-from-any-edge highest-coordinate grid) middle-trees-co-ordinates)))))

(defn part-2 [input] (let [grid (parse-input input)
                           highest-x (dec (count (first grid)))
                           highest-y (dec (count grid))
                           highest-coordinate [highest-x highest-y]
                           middle-trees-co-ordinates (generate-coordinates [1 1] highest-coordinate)]
                       (apply max (map (partial find-scenic-score-coordinate highest-coordinate grid) middle-trees-co-ordinates))))

(defn result []
  [(part-1 input) (part-2 input)])