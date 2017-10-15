(ns clojure-noob.ch07
  (:gen-class))


; EVAL
(def addition-list (list + 1 2))
(eval addition-list)

(eval (concat addition-list [10]))

; consctruct a def function and eval
(eval (list 'def 'lucky-number (concat addition-list [10])))
(println lucky-number)

(read-string "(+ 1 2)")

(list? (read-string "(+ 1 2)"))

(conj (read-string "(+ 1 2)") :zagglewag)
