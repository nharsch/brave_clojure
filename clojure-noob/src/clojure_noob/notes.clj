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


; LOOPS

; for loop
; is a sequence operator
(for [thing [1 2 3 4]]
  (* thing thing))

; like list comprehensions in python
(for [w ["bethany" "sonya" "bene"] :when (> (count w) 5)]
  w)

; recur
; tail recursion
(defn some-join [coll result]
  ; base case, return string of result + first of coll
  (if (= 1 (count coll)) (str result (first coll))
    ; call recursively with rest, string of result + first + ", "
    (recur (rest coll) (str result (first coll) ", "))))

(some-join ["hello" "world" "love" "coding"] "Words: ")

; loop 
; sets a recusion point. the recusrion point is designed to use recur
; this looks just like an anonymous recursive function, like above, no?
(loop [coll ["hello" "world" "love" "coding"] result "Words: "]
  (if (= 1 (count coll)) (str result (first coll))
      (recur (rest coll) (str result (first coll) ", "))))
"Words: hello, world, love, coding"

; Chapter 4

(defn titleize
  [topic]
  (str topic " for the Brave and True"))


(map titleize ["Hamsters" "Rangnarok"])
; works on hashes
(map titleize #{"Elbows" "Soap Carving"})
; works on anything that first and rest work on

; seq
(= 
  (seq '(1 2 3))
  (seq [1 2 3]))
; would be equal except hashes are unordere
(seq #{1 2 3})
; breaks it into [key value] 
(seq {:name "Bill Compton" :occupation "Dead mopey guy"})

(into {} '([:a 1] [:b 2] [:c 3]))
(into {} [["some" "values"] ["are" "heterogeneous"]])

; Map
(map inc [1 2 3])
(map str ["a" "b" "c"] ["A" "B" "C"])
; works kida like this
(list (str "a" "A") (str "b" "B"))

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})

; this is like python zip 
(map unify-diet-data 
     human-consumption 
     critter-consumption)
; => ({:human 8.1, :critter 0.0}
;; {:human 7.3, :critter 0.2}
;; {:human 6.6, :critter 0.3}
;; {:human 5.0, :critter 1.8})

; map can take a collection of functions
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  ; woah
  (map #(% numbers) [sum count avg]))
(stats [3 4 10])
(stats [80 1 44 13 6])

; map key lookups to map data structures
(def idenitites
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])
; because, remember, keys are functions
(map :real idenitites)

; reduce
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        ; reduce treats this map
        ; as a sequence of vectors
        {:max 30 :min 10})

; build up with k,v args
; kind of like update
(assoc (assoc {} :max (inc 30))
        :min (inc 10))
(get (assoc {} "key" "value") "key")

; "overwrite" existing values
(assoc {:key "old value"} :key "new value")

; can use reduce to filter keys from a map based
; on a value
(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1
         :critter 3.9})

; implement map with reduce
(defn mymap
  "implements map with reduce"
  [fun sq]
  (reduce (fn [new-seq val]
            (conj new-seq (fun val)))
          []
          sq))
(mymap inc [1 2 3 4])

(= (take 5 [1 2 3 4 5 6])
    (1 2 3 4 5 ))
;interesting, lists are equal to vectors
(= '(1 2) [1 2])
(= '() [])

(= (drop 5 [1 2 3 4 5 6])
    ; remember that drop returns a list
    '(6))

; take while filters against function
(= (take-while #(< % 3) [1 2 3])
    '(1 2))

(= (drop-while #(< % 3) [1 2 3])
    '(3))

; which is nice on maps
(def journal [{:month 1} {:month 2} {:month 3}])
(take-while #(< (:month %) 3) journal)
(drop-while #(< (:month %) 3) journal)
; chain them
(take-while #(< (:month %) 3) 
    (drop-while #(< (:month %) 2) 
        journal))

(filter #(> (:month %) 1) journal)

; some is like any, it will find the first true
; item in a seq
(some #(> (:month %) 1) journal)
; expect returns nil not False, which make sense
; if you thing of the sequence as infinite
(some #(> (:month %) 5) journal)
; here's how you return the first value with some
(some #(and (> (:month %) 1) %) journal)

; sort is ascending
(sort [4 2 6 1])
; sort by gives you an option
(sort-by count ["aaa" "c" "bb"])
; so this is how to do descending
(sort-by - [2 8 3])

; concat appends members of one seq to another
(= '(1 2 3 4) (concat [1 2] [3 4]))

; infinite sequences

(concat (take 8 (repeat "na")) ["Batman!"])

(take 3 (repeatedly (fn [] (rand-int 10))))

; Collection Abstraction
; similiar to sequence

(empty? [])
(empty? ["no!"])

; INTO
(map identity {:sunlight-reaction "Glitter!" :sesame "no"})
; returns vectors

(into {} (map identity {:sunlight-reaction "Glitter!"}))
; places vector into map

(map identity [:garlic :sesame-oil :fried-eggs])

(into [] (map identity [:garlic :sesame-oil :fried-eggs]))

(map identity [:garlic-clove :garlic-clove])

(into #{} (map identity [:garlic-clove :garlic-clove]))
; maps into map

(into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]])
; you can INTO non empty colls

(into ["cherry"] '("pine" "spruce"))

; conj
(conj [0] [1])
; [0 [1]]

; INTO's second arg must be coll
(into [0] [1])
; [0 1]

(conj [0] 1)
; [0 1]

(conj [0] 1 2 3 4)
; [0 1 2 3 4]

(conj {:time "midnight"} [:place "ye olde cemetarium"])

; we could define conj with into
(defn my-conj
  [target & additions]
  ; additions at this step is vector 
  (into target additions))

; apply explodes a sequable data structure so
; it can be passed to a function that expects
; a rest parameter

(= [1 2 3] (max [1 2 3]))
(= 3 (apply max [1 2 3]))

; we can use this to build into with conj

(defn my-into
  [target additions]
  (apply conj target additions))

(my-into [0] [1 2 3])

; partial

(def add10 (partial + 10))
(= 13 (add10 3))

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))
(add-missing-elements "aluminium")
(add-missing-elements "carbon")

(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    ; explode the args
    (apply partialized-fn (into args more-args))))

;using partial for a logger
(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))

(warn "RED LIGHT AHEAD")














