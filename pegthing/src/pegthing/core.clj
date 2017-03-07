(ns pegthing.core
  (require [clojure.set :as set])
  (:gen-class)) ;; means we can use in command line

(defn tri*
  "Generates laxy sequence of triangular numbers"
  ([] (tri* 0 1))
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))

(def tri (tri*))
(take 5 tri)

(defn triangular?
  "Is the number triangular?"
  [n]
  (= n (last (take-while #(>= n %) tri))))
(map triangular? '(5 6))

(defn row-tri
  "The triangular number at the end of rown n"
  [n]
  (last (take n tri)))

(map row-tri '(1 2 3))

(defn row-num
  "Returns row number the position belongs to: pos 1 in row 1, etc"
  [pos]
  (inc (count (take-while #(> pos %) tri))))

(defn connect
  "Form a mutual connection between two positions"
  [board max-pos pos neighbor destination]
  (if (<= destination max-pos)
    (reduce (fn [new-board [p1 p2]] ; reducer adds connection
              (assoc-in new-board [p1 :connections p2] neighbor))
      board
      [[pos destination] [destination pos]]) ;makes 2 way connection
    board))

(defn connect-right
  [board max-pos pos]
  (let [neighbor (inc pos)
        destination (inc neighbor)]
    (if-not (or (triangular? neighbor) (triangular? pos))
      (connect board max-pos pos neighbor destination)
      board)))

(defn connect-down-left
  [board max-pos pos]
  (let [row (row-num pos)
        neighbor (+ row pos)
        destination (+ 1 row neighbor)]
    (connect board max-pos pos neighbor destination)))

(defn connect-down-right
  [board max-pos pos]
  (let [row (row-num pos)
        neighbor (+ 1 row pos)
        destination (+ 2 row neighbor)]
    (connect board max-pos pos neighbor destination)))

(connect-down-left {} 15 1)

(defn add-pos
  "Pegs the position and performs connections"
  [board max-pos pos]
  (let [pegged-board (assoc-in board [pos :pegged] true)]
    (reduce (fn [new-board connection-creation-fn]
              (connection-creation-fn new-board max-pos pos))
      pegged-board ;call all these fns on board
      [connect-right connect-down-left connect-down-right])))

(add-pos {} 15 1)

(defn new-board
  "Creates a new board with the given number of rows"
  [rows]
  (let [initial-board {:rows rows}
        max-pos (row-tri rows)]
    (reduce (fn [board pos] (add-pos board max-pos pos))
            initial-board
            (range 1 (inc max-pos)))))

(def test-board (new-board 5))

;TODO moving pegs

(defn pegged?
  "Does the position have a paeg in it?"
  [board pos]
  (get-in board [pos :pegged]))
(assert (pegged? test-board 2))

(defn remove-peg
  "Take the peg at the given position"
  [board pos]
  (assoc-in board [pos :pegged] false))
(assert (false? 
    (get-in
      (remove-peg test-board 1)
      [1 :pegged])))

(defn place-peg
  "Put a peg in the board at a given position"
  [board pos]
  (assoc-in board [pos :pegged] true))
(assert (get-in 
         (place-peg (remove-peg test-board 1) 1)
         [1 :pegged]))

(defn move-peg
  "Take peg out of p1 and place it in p2"
  [board p1 p2]
  (place-peg (remove-peg board p1) p2))

(defn valid-moves
  "Return a map of all valid moves for pos, where the key is the
  destination and the value is the jumped position"
  [board pos]
  (into {}
        (filter (fn [[destination jumped]]
                  (and (not (pegged? board destination))
                       (pegged? board jumped)))
                (get-in board [pos :connections]))))
(def my-board (assoc-in (new-board 5) [4 :pegged] false))

(valid-moves my-board 1)

(defn valid-move?
  "Return jumped position if the move from p1 to p2 is 
  valid, nil otherwise"
  [board p1 p2]
  (get (valid-moves board p1) p2))
(valid-move? my-board 8 4)
(valid-move? my-board 1 4) ;returns jumped pos if true

(defn make-move
  "Move peg from p1 to p2, removing jumped peg"
  [board p1 p2]
  (if-let [jumped (valid-move? board p1 p2)]
    (move-peg (remove-peg board jumped) p1 p2)))

(defn can-move?
  "Do any of the pegged positions have valid moves?"
  [board]
  (some (comp not-empty (partial valid-moves board))
        (map first (filter #(get (second %) :pegged) board))))

(def alpha-start 97)
(def alpha-end 123)
(def letters (map (comp str char) (range alpha-start alpha-end)))
(def pos-chars 3)

(def ansi-styles
  {:red  "[31m"
   :green "[32m"
   :blue  "[34m"
   :reset "[0m"})

(defn ansi
  "Produce a string which will apply an ANSI style"
  [style]
  (str \u001b (style ansi-styles)))

(defn colorize
  "Apply ANSI color to text"
  [text color]
  (str (ansi color) text (ansi :reset)))

(defn render-pos
  [board pos]
  (str (nth letters (dec pos)) ;alpha
       (if (get-in board [pos :pegged]) ;pegged
         (colorize "0" :blue)
         (colorize "-" :red))))

(defn row-positions
  "Return all positions in the given row"
  [row-num]
  (range (inc (or (row-tri (dec row-num)) 0))
         (inc (row-tri row-num))))

(defn row-padding
  "String of spaces to add to the beginning of a row to center"
  [row-num rows]
  (let [pad-length (/ (* (- rows row-num) pos-chars) 2)]
      (apply str (take pad-length (repeat " ")))))

(defn render-row
  [board row-num]
  (str (row-padding row-num (:rows board))
       (clojure.string/join " " (map (partial render-pos board)
                                     (row-positions row-num)))))

(defn print-board
  [board]
  (doseq [row-num (range 1 (inc (:rows board)))]
    (println (render-row board row-num))))

(print-board my-board)
(doseq [foo [2 3]] (println foo))

(defn letter->pos
    "Converts a letter string to the corresponding position number"
    [letter]
    (inc (- (int (first letter)) alpha-start)))

(defn get-input
    "Waits for user to enter text and hit enter, then cleans the input"
    ([] (get-input nil))
    ([default]
        (let [input (clojure.string/trim (read-line))]
            (if (empty? input)
                default
                (clojure.string/lower-case input)))))

(defn characters-as-strings
    [string]
    (map str (filter #(Character/isLetter %) string)))


(defn prompt-move
    [board]
    (println "\nHere's your board:")
    (print-board board)
    (println "Move from where to where? Enter two letters:")
    (let [input (map letter->pos (characters-as-strings (get-input)))]
        (if-let [new-board (make-move board (first input) (second input))]
                 (user-entered-valid-move new-board)
                 (user-entered-invalid-move board))))


(defn user-entered-invalid-move
    "Handles the next stip after a user has entered an invalid move"
    [board]
    (println "\n!! That was an invalid move :(\n")
    (prompt-move board))

(defn game-over
    "Announces the game is over and prompt the play again"
    [board]
    (let [remaining-pegs (count (filter :pegged (vals board)))]
        (println "Game over! You had" remaining-pegs "pags left:")
        (print-board board)
        (println "Play again? y/n [y]")
            (let [input (get-input "y")]
                (if (= "y" input)
                    (prompt-rows)
                    (do
                        (println "Bye!")
                        (System/exit 0))))))

(defn prompt-empty-peg
    [board]
    (println "Here's your board:"
        (print-board board)
        (println "Remove which peg? [e]")
        (prompt-move (remove-peg board (letter->pos (get-input "e"))))))

(defn prompt-rows
    []
    (println "How many rows? [5]")
        (let [rows (Integer. (get-input 5))
              board (new-board rows)]
            (prompt-empty-peg board)))

;; (defn -main
;;     []
;;     (prompt-rows))
