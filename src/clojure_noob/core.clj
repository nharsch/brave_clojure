(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I'm a little teapot!"
  [& args]
  (println "Hello, World!"))


(if true
  "By Agthar's hammer!"
  "nope")

(if false
  "By Odin's Elbow!")

(if true
  (do (println "Success!")
      "By Zs hammer")
  (do (println "Failure!")
      "By A's trident"))

(when true
  (println "Success!")
  "abra cadabra")

(def failed-hero-names
  ["Larry Potter" "Doreen" "Bulk"])

(def actual-names
  ["nigel" "bethany"])

(=
  (nth actual-names 1)
  "bethany")

(defn error-message
  [severity]
  (str "OH GOD! DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED!"
         "DOOMED")))

(error-message :mild)

; maps
(=
  (get {:a 0 :b 1} :n)
  nil)
(=
  (get {:a 0 :b 1} :n 3)
  3)

; get-in
(=
  (get-in {:a 0 :b {:c "ho hum"}} [:b :c])
  "ho hum")

; treat map like a function
(=
  ({:name "The Human Coffeepot"} :name)
  "The Human Coffeepot")

; keywords as functions
(=
 (:a {:a 1 :b 2 :c 3})
 1)

(=
 (:d {:a 1 :b 2} "some other")
 "some other")

; Vectors
[3 2 1]

(=
  (get [3 2 1] 0)
  3)

(
 (get ["a" {:name "pugs"}] 1)
 :name)

(=
  (vector "creepy" "full" "moon")
  ["creepy" "full" "moon"])

; conj
(=
 (conj [1 2 3] 4)
 [1 2 3 4])

; lists
'(1 2 3 4)
(nth '(1 2 3 4) 0)

(=
 (list 1 "two" {3 4})
 '(1 "two" {3 4}))

; conj puts item at front
(conj
  '(1 2 3) 4)

(=
 (hash-set 1 1 2 2)
 #{1 2})






