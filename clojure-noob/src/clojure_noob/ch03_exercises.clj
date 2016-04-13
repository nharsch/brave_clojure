(ns clojure-noob.ch03_exercises
  (:gen-class))

; 1

; str
(str "lets make some" "string from other" "strings and " 
     " maybe numbers" 123 " and keywords " :keyword)
(str "and what about data structs? " (list 1 2 3) [4 5 6] {:one 1 :two 2})

; vector
(= 
  (vector 1 2 3)
  [1 2 3])

; hash-map
(hash-map :one 1 :two 2)
; can what about duplicate keys?
(=  (hash-map :one 1 :one "one")
    ; last arg
    {:one "one"})

; hash-set
(hash-set 1 2 4 4 3 :one)

; 2
(defn add-100 [n]
  (+ n 100))
(add-100 1)

; 3
(defn dec-maker [n]
  (fn [x] (- x n)))
(def dec9 (dec-maker 9))
(dec9 10)

; 4
(defn mapset [f values]
  (set (map f values)))
(mapset inc [1 1 2 2])

; 5
; radial body symmetry
;; (defn matching-part
;;   "return a map with left replaced with right"
;;   [part]   
;;   {:name (clojure.string/replace (:name part) #"^left-" "right-")
;;
;;   :size (:size part)})

; test multiplying data struct
(defn n_list [n h]
  "just want to build a list of n number of copies"
  (if (= n 1)
  h
  (recur (- n 1) h)))

(n_list 2 {:name "hand"})

(defn radial-symmetrize-body-parts 
  [radial-factor asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (* radial-factor part)))))

; Chapter 4

(defn titleize
  [topic]
  (str topic " for the Brave and True"))


(map titleize ["Hamsters" "Rangnarok"])
; works on hashes
(map titleize #{"Elbows" "Soap Carving"})
; works on anything that first and rest work on

