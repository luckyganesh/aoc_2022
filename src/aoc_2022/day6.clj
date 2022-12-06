(ns aoc-2022.day6)

(def input (slurp "resources/day6.txt"))

(def small-input "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")

(defn are-different-characters [characters]
  (= (count characters) (count (set characters))))

(defn index-of-first-match [pred coll]
  (loop [array coll n 0]
    (if (pred (first array))
      n
      (recur (rest array) (inc n)))))

(defn solution [no-of-characters input]
  (->> input
       (partition no-of-characters 1)
       (index-of-first-match are-different-characters)
       (+ no-of-characters)))

(def part-1 (partial solution 4))
(def part-2 (partial solution 14))

(defn result []
  [(part-1 input) (part-2 input)])