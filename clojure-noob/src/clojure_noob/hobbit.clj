(ns clojure-noob.hobbit
  (:require clojure-noob.core)
  (:gen-class))


(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  "return a map with left replaced with right"
  [part]                                    ;#"" means regex
  {:name (clojure.string/replace (:name part) #"^left-" "right-")

  :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  ; setup loop
  ; loop is like an anonymous funciton, where recur calls itself
  (loop, [remaining-asym-parts asym-body-parts
    ; accumulator
    final-body-parts []]
    (if (empty? remaining-asym-parts)
      ; base case
      final-body-parts
      ; let binds names to values
      ; in this case, pat is car, remaining is cdr of remaining-asym-parts
      (let [[part & remaining] remaining-asym-parts]
        ; call loop again, with remaining as r-asym
        (recur remaining
          ; place this into vector
          (into final-body-parts
            ; a set of part and result of matching-part of part
            (set [part (matching-part part)])))))))

(symmetrize-body-parts asym-hobbit-body-parts)

; let binds names to values
(let [x 3] x)

(def dalmation-list
  ["Pongo" "Perdita" "Puppy 2" "Puppy 1"])

; take grabs some number of elements from front of list
(let [dalmatians (take 2 dalmation-list)]
  dalmatians)

; **LET** creates a new namespace
(def x 0)
(< x (let [x 1] x))

; you assign multiple values?
(let [a 1 b 2 c 3] (list a b c))

; however, withing the let statement, you can refer to existing
; ns
(def x 0)
(= 1 (let [x (inc x )] x))

; ok, we can do this better with reduce
(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
              ; acc name        ;item
  (reduce (fn [final-body-parts part]
            ; put into acc          ;set of part and matching part
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
      (loop [[part & remaining] sym-parts
             accumulated-size (:size part)]
        (if (> accumulated-size target)
          part
          (recur remaining (+ acculmulated-size (:size (first remaining))))))))





