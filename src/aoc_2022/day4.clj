(ns aoc-2022.day4
  (:require [aoc-2022.utils :as utils]))

(def input (slurp "resources/day4.txt"))
(def small-input "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(defn parse-input [string]
  (->> string
       (utils/split-by-comma)
       (map utils/split-by-dash)
       (mapv (partial mapv utils/parse-to-int))))

(defn is-sub-section [[main-start main-end] [sub-start sub-end]]
  (and (<= main-start sub-start) (>= main-end sub-end)))

(defn is-any-sub-section [[section1 section2]]
  (or (is-sub-section section1 section2) (is-sub-section section2 section1)))

(defn is-in-between [[main-start main-end] sub-start]
  (and (<= main-start sub-start) (>= main-end sub-start)))

(defn is-any-overlap [[[main-start main-end] [sub-start sub-end]]]
  (or
    (is-in-between [main-start main-end] sub-start)
    (is-in-between [main-start main-end] sub-end)
    (is-in-between [sub-start sub-end] main-start)
    (is-in-between [sub-start sub-end] main-end)))

(defn part-1 [input]
  (->> input
       utils/split-into-lines
       (map parse-input)
       (filter is-any-sub-section)
       count))

(defn part-2 [input]
  (->> input
       utils/split-into-lines
       (map parse-input)
       (filter is-any-overlap)
       count))

(defn result [] [(part-1 input) (part-2 input)])