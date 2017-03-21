(ns pegthing.ch4_excercises)

;1
(defn attr
  [attr]
  (comp attr :attributes))
((attr :test) {:attributes {:test "yeah"}})

;2
(defn my-comp
  ([f] f)
  ([f1 f2] #(f2 (f1 %)))
  ([f1 f2 & more]
    (reduce my-comp (my-comp f1 f2) more)))
((my-comp - inc str) 5)

;TODO: exc3
(assoc {} :key "value")
(assoc-in)
(defn my-assoc-in
  [m [k & ks] v]
  (if ks
    (assoc m k  ;assoc the value at first key
           (assoc-in  ;recursively call assoc-in
            (get m k)  ;the value at first key
            ks  ;rest of the keys
            v  ;the value
            ))
    (assoc m k v)))  ;base case
(=
 (my-assoc-in {:foo {:bar ""}} [:foo :bar] "baz")
 {:foo {:bar "baz"}})

(update-in)

; recursive get-in?

(defn my-update-in
  [m ks f]
  (my-assoc-in m ks
               (f (get-in m ks))
 ))

(def my-map
  {:person {:name "Nigel" :age 30}})

(my-assoc-in my-map [:person :age] (inc (get-in my-map [:person :age])))

(my-update-in {:person {:age 30}} [:person :age] inc)

(get-in my-map [:person :age])

(assert
 (=
  (get-in
   (my-update-in my-map [:person :age] inc)
   [:person :age])
 31)
)
