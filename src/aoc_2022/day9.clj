(ns aoc-2022.day9
  (:require [aoc-2022.utils :as utils]))

(def input (slurp "resources/day9.txt"))

(def small-input "R 5\nU 8\nL 8\nD 3\nR 17\nD 10\nL 25\nU 20")

(defn parse-move [move]
  (let [[direction-in-str length-in-str] (utils/split-by-space move)]
    {:direction (keyword direction-in-str)  :length (utils/parse-to-int length-in-str)}))

(def parse-input (comp (partial map parse-move) utils/split-into-lines))

(def initial-position-part1 {:HEAD [0 0] :TAILS [[0 0]] :positions-of-tail #{[0 0]}})
(def initial-position-part2 {:HEAD [0 0] :TAILS (vec (repeat 9 [0 0])) :positions-of-tail #{[0 0]}})

(defn next-pos-in-direction [fn1 fn2 [x y]]
  [(fn1 x) (fn2 y)])

(def LEFT (partial next-pos-in-direction identity dec))
(def RIGHT (partial next-pos-in-direction identity inc))
(def DOWN (partial next-pos-in-direction inc identity))
(def UP (partial next-pos-in-direction dec identity))

(defn abs [n] (max n (- n)))

(defn get-next-pos-head [dir position]
  (let [direction-to-move (get {:R RIGHT :L LEFT :U UP :D DOWN} dir)]
    (direction-to-move position)))

(defn is-neighbour [pos1 pos2]
  (let [difference (abs (- pos1 pos2))]
    (or (zero? difference) (= 1 difference))))

(defn is-neighbour-position [[x1 y1] [x2 y2]]
  (and (is-neighbour x1 x2) (is-neighbour y1 y2)))

(defn get-next-axis-fn [head-axis tail-axis]
  (cond
    (= head-axis tail-axis) identity
    (> head-axis tail-axis) inc
    :else dec))

(defn get-next-pos-tail [[head-x head-y] [old-tail-x old-tail-y]]
  (if (is-neighbour-position [head-x head-y] [old-tail-x old-tail-y])
    [old-tail-x old-tail-y]
    [((get-next-axis-fn head-x old-tail-x) old-tail-x) ((get-next-axis-fn head-y old-tail-y) old-tail-y)]))

(defn get-next-pos-tails [head tails]
  (utils/map-reduce get-next-pos-tail head tails))

(defn move-HT [position instruction]
  (loop [position position n 0]
    (if (= n (:length instruction))
      position
      (let [next-head (get-next-pos-head (:direction instruction) (:HEAD position))
            next-tails (get-next-pos-tails next-head (:TAILS position))
            updated-positions-of-tail (conj (:positions-of-tail position) (last next-tails))]
        (recur {:HEAD next-head :TAILS next-tails :positions-of-tail updated-positions-of-tail} (inc n))))))

(def part-1 (comp count :positions-of-tail (partial reduce move-HT initial-position-part1) parse-input))
(def part-2 (comp count :positions-of-tail (partial reduce move-HT initial-position-part2) parse-input))

(defn result []
  [(part-1 input) (part-2 input)])