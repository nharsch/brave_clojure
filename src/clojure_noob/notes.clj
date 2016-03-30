(ns clojure-noob.notes
  (:require clojure-noob.core)
  (:gen-class))


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

(set [3 3 3 4 4])

(contains? #{:a :b} :a)

; FUNCTIONS

; woah
(or + -)

; no way
((or + -) 1 2 3)

(= ((and (= 1 1) +) 1 2 3)
   6)

(=
 ((first [+ 0]) 1 2 3)
 6)

; throws an error because not a function
;(1 2 3 4)

(inc 1.1)

; here is a higher order function
; **note** that map doesn't return vector
(=
 (map inc [0 1 2 3])
 (1 2 3 4))

;; (defn map2vec 
;;   "map returns vector"
;;   [func iterable]
;;   (list (map func iterable)))
;;
;;
;; (map2vec inc [0 1])
;;

; special forms

(defn too-enthusiastic
  "Return a cheer that might be a bit too enthusiastic"
  [name]
  (str "OH ME GOD " name " YOU'RE THE BEST"))

(too-enthusiastic "Zelda")

; 0-arite
(defn no-params
  []
  "I take no parameters!")
; 1-arity
(defn one-param
  [x]
  (str "I take one parameter: " x))
; 2-arity
(defn two-params
  [x y]
  (str "Two parameters! Thhat's nothing!" x y))

; different behavior for different args
(defn multi-arity
  ([first-arg]
   (println first-arg))
  ([first-arg second-arg]
   (println first-arg second-arg ))
  ([first-arg second-arg third-arg]
   (println first-arg second-arg third-arg)))

; this is cool
(defn x-chop
  "Describe the chop you're inflicting"
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that"))
  ([name]
   ; just call self with def arg
   (x-chop name "karate")))

(x-chop "Kanye West" "slap")
(x-chop "Kanye East")

; rest param
(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))

(defn codger
  ; variable arity, & means possible multiples
  [& whippersnappers]
  (map codger-communication whippersnappers))

(codger "Billy" "Anne-Marie" "The Incredible Bulk")
(codger "You")

(defn favortie-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join ", " things)))

(favortie-things "Doreen" "gum" "shoes" "kara-te")

; DECONSTRUCTING
(defn my-first
  [[first-thing]] ; first thing within a vector
  first-thing second-thing) 

(my-first ["oven" "bike" "war-axe"])

(defn chooser
  ; cool unpacking, much like Pythons
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))

(chooser ["Marmalade", "Handsmore Jack", "Pigpen", "Aquaman"])

(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lngs: " lng)))

(announce-treasure-location {:lat 28.22 :lng 81.33})

(defn announce-treasure-location
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(announce-treasure-location {:lat 28.22 :lng 81.33})

(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))
  treasure-location)

(receive-treasure-location {:lat 1 :lng 2})

; functions automatically return last form
(defn illustratice-function
  []
  (+ 1 304)
  30
  "joe")

(illustratice-function)

(defn number-comment
  [x]
  (if (> x 6)
    "Big number"
    "small number"))

(map number-comment [4 10])

; anonymouse functions
(map (fn [name] (str "Hi, " name))
     ["Darth Vader" "Mr. Magoo"])

((fn [x] (* x 3)) 8)

; defn is just the same as def applied to a function
(def my-special-multiplier (fn [x] (* x 3)))
(my-special-multiplier 12)

; super compact anon functions
(#(* % 3) 3)

(map #(str "Hi, " %)
     ["darth Vader" "Mr. Magoo"])

; function call
(* 8 3)

; anon function, the % stands for a var
#(* % 3)

(#(str %1 " and " %2) "cornbread" "butter beans")

; and sweet rest params
(#(identity %&) 1 "blarg" :yip)

; returning functions
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))
(inc3 3)

((fn [inc num] ((inc-maker inc) num)) 2 3)

