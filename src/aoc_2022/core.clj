(ns aoc-2022.core
  (:require [aoc-2022.day3 :as day3]
            [aoc-2022.day4 :as day4])
  )


(defn main []
  (println "Day 3 result" (day3/result))
  (println "Day 4 result" (day4/result)))

(main)