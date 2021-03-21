(ns clojure-noob.ch09
  (:gen-class))



(def yak-butter-international
  {:store "Yak Butter International"
    :price 90
    :smoothness 90})

(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smoothness 83})

;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

;; future calls body on separate thread
(def test-future (future (Thread/sleep 8000)
                         (println "future done")
                         "future return"
                         ))

;; deref future either blocks if not done or returns returned val if done
@test-future


;; delays allow you to define a task without having to execute it or require the
;; result immediately
(def test-delay (delay "delay return"))
;; force and deref are equivalent
(force test-delay)
@test-delay


;; promises define expected result, which can be dlivered later
;; if you deref a non delivered promise, repl will block
(def test-promise (promise))
(deliver test-promise "deliver ")
@test-promise
