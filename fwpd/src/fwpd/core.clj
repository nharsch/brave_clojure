(ns fwpd.core)
(require '[clojure.string :as str])
(def filename "suspects.csv")
(slurp filename)

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))
(= (str->int "1") 1)

; return map to functions
(def conversions {:name identity
                  :glitter-index str->int})

; takes key/val returns
(defn convert
  [vamp-key value]
  ; pull vamp-key fn out of conversions, call on val
  ((get conversions vamp-key) value))

(convert :glitter-index 0)

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(parse (slurp filename))

(defn mapify
  "Return a seq of maps"
  [rows] ; list of vectors
  (map (fn [unmapped-row]
         (reduce
           (fn [row-map [vamp-key value]]
             ; return row-map updated with vampkey converted
             (assoc row-map vamp-key (convert vamp-key value)))
           {}
           ; returns list of vectors with k/v
           (map vector vamp-keys unmapped-row)))
    rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(map vector vamp-keys (first (parse (slurp filename))))
(first (mapify (parse (slurp filename))))

(glitter-filter 3 (mapify (parse (slurp filename))))
;({:name "Edward Cullen", :glitter-index 10} ...)

;; ch 4 exercises

;1. turn the result of your glitter filter into a list on names
(map
  :name
  (glitter-filter 3 (mapify (parse (slurp filename)))))

; 2
; Write a function, append, which will append a new suspect to your list of suspects.
(defn append
  [perp-list suspect]
  (conj perp-list suspect))
(append [{:name "nigel" :glitter-index 0}] {:name "bethany" :glitter-index 4})

; 3
;; Write a function, validate, which will check that :name and :glitter-index are present 
;; when you append. The validate function should accept two arguments: a map of 
;; keywords to validating functions, similar to conversions, and the record to be validated.
(defn validate-perp
  "ensure keywords are present in map"
  [keywords perp-map]
  (every?
    #(contains? perp-map %)
    keywords)
)
(true? (get {:name "foo"} :name false))
(validate-perp vamp-keys {:name "foo"})
(validate-perp vamp-keys {:name "foo" :glitter-index 0})

; Write a function that will take your list of maps and convert it back to a 
; CSV string. You’ll need to use the clojure.string/join function.


(defn map-to-row
  [row-keys row-map]
  (clojure.string/join ","
    (map
      #(get row-map %)
      row-keys)))
(map-to-row [:bar :foo] {:foo "foo" :bar "bar"})

(defn list-to-csv
  [headers list-of-perps]
  (clojure.string/join "\n"
    (map
      (partial map-to-row headers)
      list-of-perps)))

(assert (= (list-to-csv vamp-keys (mapify (parse (slurp filename))))
           (clojure.string/trim (slurp filename))))
(clojure.string/join "," '("Nigel" "James"))

