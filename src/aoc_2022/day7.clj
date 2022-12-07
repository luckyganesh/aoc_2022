(ns aoc-2022.day7
  (:require [clojure.string :as str]
            [aoc-2022.utils :as utils]))

(def input (slurp "resources/day7.txt"))

(def small-input "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(def system-store {:filesystem {:/ {}} :pwd []})

(def split-by-command (comp rest (partial utils/split #"\$")))

(defn parse-command-output [input]
  (let [array (->> input
                   utils/split-into-lines)
        command-with-parameters (utils/split-by-space (str/trim (first array)))
        command (keyword (first command-with-parameters))
        parameters (rest command-with-parameters)
        output (rest array)]
    {:command command :parameters parameters :outputs output}))

(def parse-input (comp (partial map parse-command-output) split-by-command))

(defn push-element [element coll] (conj coll element))

(def pop-element (comp vec drop-last))

(defn cd-command [system-store parameters]
  (let [dir-name (first parameters)]
    (if (= dir-name "..") (update-in system-store [:pwd] pop-element)
                         (update-in system-store [:pwd] (partial push-element (keyword dir-name))))))

(defn add-element-in-object [path name value object]
  (assoc-in object (conj path name) value))

(defn update-file-structure [system-store output]
  (let [[dir-or-size name] (utils/split-by-space output)
        keyword-name (keyword name)]
    (if (= "dir" dir-or-size)
      (update-in system-store [:filesystem] (partial add-element-in-object (:pwd system-store) keyword-name {}))
      (update-in system-store [:filesystem] (partial add-element-in-object (:pwd system-store) keyword-name (utils/parse-to-int dir-or-size)))
      )))

(defn ls-command [system-store outputs]
  (reduce update-file-structure system-store outputs))

(defn collect-data-from-cwo [system-store cwo]
  (condp = (:command cwo)
    :cd (cd-command system-store (:parameters cwo))
    :ls (ls-command system-store (:outputs cwo))))

(defn df
  ([file-system] (df file-system {} ""))
  ([file-system directories path]
   (reduce
     (fn [[size directories] [key value]]
             (if (number? value) [(+ size value) directories]
                                 (let [
                                       dir-path (str/join [path key])
                                       [dir-size update-dir] (df value directories dir-path)
                                       added-dir (assoc update-dir dir-path dir-size)
                                       ]
                                   [(+ size dir-size) added-dir]
                                   )))
           [0 directories] file-system)))


(defn part-1 [input]
  (let [commands-with-outputs (parse-input input)
        final-system-store (reduce collect-data-from-cwo system-store commands-with-outputs)
        df-of-directories (second (df (:filesystem final-system-store)))]
    (apply + (filter (partial >= 100000) (map second df-of-directories)))))

(defn part-2 [input]
  (let [commands-with-outputs (parse-input input)
        final-system-store (reduce collect-data-from-cwo system-store commands-with-outputs)
        [total-occupied-space df-of-directories] (df (:filesystem final-system-store))
        ]
    (first (sort (filter (partial < (- total-occupied-space 40000000)) (map second df-of-directories))))))


(defn result []
  [(part-1 input) (part-2 input)])