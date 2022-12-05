(ns aoc-2022.core
  (:require
    [aoc-2022.day1 :as day1]
    [aoc-2022.day2 :as day2]
    [aoc-2022.day3 :as day3]
    [aoc-2022.day4 :as day4]
    [aoc-2022.day5 :as day5]
    ))


(defn main []
  (println "Day 1 result" (day1/result))
  (println "Day 2 result" (day2/result))
  (println "Day 3 result" (day3/result))
  (println "Day 4 result" (day4/result))
  (println "Day 5 result" (day5/result))
  )

(main)