(ns aoc-2022.core
  (:require
    [aoc-2022.day1 :as day1]
    [aoc-2022.day2 :as day2]
    [aoc-2022.day3 :as day3]
    [aoc-2022.day4 :as day4]
    [aoc-2022.day5 :as day5]
    [aoc-2022.day6 :as day6]
    [aoc-2022.day7 :as day7]
    [aoc-2022.day8 :as day8]
    ))


(defn main []
  (println "Day 1 result" (day1/result))
  (println "Day 2 result" (day2/result))
  (println "Day 3 result" (day3/result))
  (println "Day 4 result" (day4/result))
  (println "Day 5 result" (day5/result))
  (println "Day 6 result" (day6/result))
  (println "Day 7 result" (day7/result))
  (println "Day 8 result" (day8/result))
  )

(main)