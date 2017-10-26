(ns clojure-noob.ch07
  (:gen-class))


; EVAL
(def addition-list (list + 1 2))
(eval addition-list)

(eval (concat addition-list [10]))
; => 13

; consctruct a def function and eval
(eval (list 'def 'lucky-number (concat addition-list [10])))
(println lucky-number)

(read-string "(+ 1 2)")

(list? (read-string "(+ 1 2)"))

(conj (read-string "(+ 1 2)") :zagglewag)

; macros

(eval
  (let [infix (read-string "(1 + 1)")]
    (list (second infix) (first infix) (last infix))))

(defmacro infix
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))

(infix (1 + 2))

(macroexpand '(infix (1 + 2)))

(defn read-resource
  "Read a resource into a string"
  [path]
  (read-string (slurp (clojure.java.io/resource path))))

(defn read-resource
  [path]
  (-> path
      clojure.java.io/resource
      slurp
      read-string))

(read-resource "test.clj")

;; exercises

; 1

(eval (list (read-string "println") (quote "Nigel")  "Spinal Tap"))

; 2

; how to find infix triples
; (n1 + n2 + n3) == (+ (+ n1 n2) n3)

(defmacro infix
  [infixed]
  (list (second infixed) (first infixed) (last infixed)))


(defn infix-all-fn
    [form]
    (if (= (count form) 3)
        (list (second form) (first form) (last form))
        (infix-all-fn
            (cons
                (infix-all-fn (take 3 form))
                (drop 3 form)))))

; TODO: make operator precedence

(defmacro infix-all
    [form]
    (infix-all-fn form))

(infix-all '(1 + 2))
(infix-all-fn '(1 + 2 + 3))
(infix-all (1 + 2 + 3))

; resolve only infix triples with given operator

(defn infix
    [form]
    ( ->
        ()
        ))





