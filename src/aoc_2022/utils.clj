(ns aoc-2022.utils
  (:require [clojure.string :as str]))



(defn split [separator string]
  (str/split string separator))

(def split-into-lines (partial split #"\n"))


(defn parse-to-int [x] (Integer/parseInt x))
