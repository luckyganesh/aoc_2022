(ns aoc-2022.utils
  (:require [clojure.string :as str]))



(defn split [separator string]
  (str/split string separator))

(def split-into-lines (partial split #"\n"))
(def split-by-comma (partial split #","))
(def split-by-dash (partial split #"-"))
(def split-into-characters (partial split #""))


(defn parse-to-int [x] (Integer/parseInt x))
